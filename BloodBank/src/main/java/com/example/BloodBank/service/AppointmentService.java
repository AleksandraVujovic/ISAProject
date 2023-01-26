package com.example.BloodBank.service;

import com.example.BloodBank.dto.appointmentDTOs.BookAppointmentDTO;
import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.AppointmentStatus;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.service.service_interface.IQrCodeService;
import com.example.BloodBank.repository.AppointmentRepository;
import com.example.BloodBank.repository.CustomerRepository;
import com.example.BloodBank.service.service_interface.IAppointmentService;
import com.example.BloodBank.service.service_interface.IQuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import java.awt.image.BufferedImage;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class AppointmentService implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final IQuestionnaireService questionnaireService;
    private final CustomerRepository customerRepository;
    private final EmailSenderService emailSenderService;

    private final IQrCodeService qrCodeService;


    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,
                              CustomerRepository customerRepository,
                              EmailSenderService emailSenderService,
                              IQuestionnaireService questionnaireService, IQrCodeService qrCodeService) {
        this.appointmentRepository = appointmentRepository;
        this.questionnaireService = questionnaireService;
        this.customerRepository = customerRepository;
        this.emailSenderService = emailSenderService;
        this.qrCodeService = qrCodeService;
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment Create(Appointment entity) throws Exception {
        return appointmentRepository.save(entity);
    }

    @Override
    public Appointment Read(Long id) throws Exception {
        return appointmentRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment Update(Appointment entity) throws Exception {
        if(entity.getExecuted() == AppointmentStatus.CANCELLED){
            //TODO: dodati penal
            entity.setTakenBy(appointmentRepository.findById(entity.getId()).get().getTakenBy());
            entity.setLocation(appointmentRepository.findById(entity.getId()).get().getLocation());
            entity.setVersion(appointmentRepository.findById(entity.getId()).get().getVersion());
            return appointmentRepository.save(entity);
        }else{
            return appointmentRepository.save(entity);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void Delete(Appointment entity) throws Exception {
        appointmentRepository.delete(entity);
    }

    @Override
    public Iterable<Appointment> GetAll() throws Exception {
        return appointmentRepository.findAll();
    }
    public Iterable<Appointment> GetForCustomerId(String id) {
        return  appointmentRepository.findByCustomerId(Long.valueOf(id));
    }

    @Override
    public List<Appointment> GetByCustomerId(Long id) throws Exception {
        List<Appointment> retVal = new ArrayList<>();
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        for(Appointment appointment : appointments) {
            if(appointment.getTakenBy() != null){
                Long aId = appointment.getTakenBy().getId();
                boolean isTrue = aId.equals(id);
                if(isTrue){
                    retVal.add(appointment);
                }
            }

        }
        return  retVal;
    }

    public Page<Appointment> GetAllPageable(Pageable page) throws Exception {
        return appointmentRepository.findAll(page);
    }
    public Page<Appointment> GetAllPageableFree(Pageable page) throws Exception {
        return appointmentRepository.findAllAvailable(page);
    }

    public Page<Appointment> GetAllPageableFreeDateFilter(Pageable page, Date startDate, Time startTime) throws Exception {
        return appointmentRepository.findAllAvailableDateFilter(page, startDate, startTime);
    }
    @Transactional(readOnly = false)

    public Boolean BookAppointment(BookAppointmentDTO dto) throws Exception {
        Appointment appointment = appointmentRepository.findById(dto.appointmentId).orElseThrow();
        //check if appointment is free
        if(appointment.getExecuted() != AppointmentStatus.FREE){
            throw new Exception("Appointment is not free");
        }
        //check if customer filled questionnaire
        if(!questionnaireService.checkQuestionnaire(dto.customerId)){
            throw new Exception("Invalid questionnaire");
        }
        //check if customer donated blood in last 6 months
        List<Appointment> appointmentList = appointmentRepository.findAllAppointmentsIn6MonthPeriod(dto.customerId, Date.valueOf(LocalDate.now().minusMonths(6)));
        if(!appointmentList.isEmpty()){
            throw new Exception("Already did a blood extraction in a 6 month period");
        }
        //book appointment
        appointment.setExecuted(AppointmentStatus.PENDING);
        appointment.setTakenBy(customerRepository.findById(dto.customerId).orElseThrow());
        String uuid = UUID.randomUUID().toString();
        appointment.setConfirmationCode(uuid);
        appointmentRepository.save(appointment);
        
        //send verification
        SendConfirmationCode(appointment);



        return true;
    }
    
    @Transactional(readOnly = false)
    public Appointment CancelAppointment(BookAppointmentDTO dto) throws Exception {
        Appointment appointment = appointmentRepository.findById(dto.appointmentId).get();
        Customer customer = customerRepository.findById(dto.customerId).get();

        //provera da li je za manje od 24h

        //provera da li je za appointment trenutno dodeljen ovaj korisnik
        if(appointment.getTakenBy().getId() != customer.getId()){
            return appointment;
        }
        //provera da li je booked ili pending
        if(appointment.getExecuted() != AppointmentStatus.BOOKED && appointment.getExecuted() != AppointmentStatus.PENDING){
            return appointment;
        }
        //namesti polja
        appointment.setExecuted(AppointmentStatus.FREE);
        appointment.setTakenBy(null);
        appointmentRepository.save(appointment);
        return appointment;

    }
    private void SendConfirmationCode(Appointment app) throws Exception {
        Customer customer = customerRepository.findById(app.getTakenBy().getId()).get();
        String activationLink = "http://localhost:8086/api/appointment/confirm/"+ app.getConfirmationCode();
        Map<String, DataSource> files = new HashMap<>();
        PrepareQrCode(activationLink, files);
        emailSenderService.sendMailWithInlineResources(customer.getEmail(), "Confirm booked appointment", "Appointment activation link is:" + activationLink, files);
    }

    private void PrepareQrCode(String activationLink, Map<String, DataSource> files) throws Exception {
        BufferedImage qrCodeImage = qrCodeService.generateQRCodeImage(activationLink, 200, 200);
        byte[] qrCodeBytes = qrCodeService.toByteArrayFromBufferedImage(qrCodeImage);
        files.put("activation.png", new ByteArrayDataSource(qrCodeBytes, "image/png"));
    }

    @Transactional(readOnly = false)
    public Appointment ConfirmAppointment(String confirmationCode) throws Exception {
        try {
            Appointment app = appointmentRepository.findByConfirmationCode(confirmationCode).get();
            app.setExecuted(AppointmentStatus.BOOKED);
            appointmentRepository.save(app);
            return app;
        } catch (Exception ex) {
            throw new Exception("Error confirming booking");
        }
    }

    @Override
    public List<Appointment> getDoneAndPendingAppointmentsForBloodBank(Long bankId) {
        List<Appointment> appointments = appointmentRepository.findDoneAndPendingAppointmentsByBankId(bankId);
//        List<Appointment> doneAndPendingApp = new ArrayList<Appointment>();
//        for(Appointment a : appointments){
//            if((a.getExecuted().equals(AppointmentStatus.DONE) || a.getExecuted().equals(AppointmentStatus.PENDING))
//                    && a.getLocation().getBankID() == bankId)
//                doneAndPendingApp.add(a);
//        }
//        return doneAndPendingApp;
        return appointments;
    }
}
