package com.example.BloodBank.service;

import com.example.BloodBank.dto.BloodBankDTO;
import com.example.BloodBank.dto.RegistrationBloodBankDTO;
import com.example.BloodBank.exceptions.EntityDoesntExistException;
import com.example.BloodBank.model.BloodBank;
import com.example.BloodBank.model.ScheduledOrder;
import com.example.BloodBank.repository.AddressRepository;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.repository.BloodRepository;
import com.example.BloodBank.service.service_interface.IBloodBankService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class BloodBankService implements IBloodBankService {
    private final BloodBankRepository bloodBankRepository;
    private final AddressRepository addressRepository;

    private final BloodRepository bloodRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    public BloodBankService(BloodBankRepository bloodBankRepository, AddressRepository addressRepository,BloodRepository bloodRepository) {
        this.bloodBankRepository = bloodBankRepository;
        this.addressRepository = addressRepository;
        this.bloodRepository = bloodRepository;
    }

    public boolean checkForBlood(String bankEmail, String bloodType, int quantity){

        for(BloodBank bank : bloodBankRepository.findAll()){
            if(bank.getEmail().equals(bankEmail)){
                switch (bloodType) {
                    case "Aplus":
                        if(bank.getBlood().getAplus() == 0)
                            return false;
                        return bank.getBlood().getAplus() >= quantity;
                    case "ABplus":
                        if(bank.getBlood().getABplus() == 0)
                            return false;
                        return bank.getBlood().getABplus() >= quantity;
                    case "Bplus":
                        if(bank.getBlood().getBplus() == 0)
                            return false;
                        return bank.getBlood().getBplus() >= quantity;
                    case "Oplus":
                        if(bank.getBlood().getOplus() == 0)
                            return false;
                        return bank.getBlood().getOplus() >= quantity;
                    case "Aminus":
                        if(bank.getBlood().getAminus() == 0)
                            return false;
                        return bank.getBlood().getAminus() >= quantity;
                    case "ABminus":
                        if(bank.getBlood().getABminus() == 0)
                            return false;
                        return bank.getBlood().getABminus() >= quantity;
                    case "Bminus":
                        if(bank.getBlood().getBminus() == 0)
                            return false;
                        return bank.getBlood().getBminus() >= quantity;
                    case "Ominus":
                        if(bank.getBlood().getOminus() == 0)
                            return false;
                        return bank.getBlood().getOminus() >= quantity;
                }
            }
        }
        return false;
    }

    public void checkAPIKey(String bankEmail, String APIKey) throws IllegalAccessException {

        Optional<BloodBank> bank = bloodBankRepository.findByEmail(bankEmail);
        if(!bank.isPresent())
            throw new IllegalStateException("Bank with that kind of email doesn't exist!");

        if(APIKey.equals("") || !APIKey.contains(bank.get().getAPIKey()))
            throw new IllegalAccessException("Authorization failed!");
    }

    @Transactional
    public void registerBloodBank(RegistrationBloodBankDTO registrationBloodBankDTO){
        BloodBank bloodBank = modelMapper.map(registrationBloodBankDTO, BloodBank.class);
        bloodBank.setStartDayWorkTime(Time.valueOf(LocalTime.of(registrationBloodBankDTO.getStartTime(), 0)));
        bloodBank.setEndDayWorkTime(Time.valueOf(LocalTime.of(registrationBloodBankDTO.getEndTime(), 0)));
        bloodBank.setNumberOfWorkingDaysInWeek(5);
        try{
            bloodBankRepository.save(bloodBank);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Can't save bank!");
        }

    }

    @Override
    public List<BloodBank> getAll() throws Exception {
        try {
            return bloodBankRepository.findAll();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<BloodBank> getBanksByRatingRange(String filter, Pageable page) throws Exception {
        String[] numbers = filter.split("[|]");
        if(numbers.length != 2){
            throw new Exception("Error in filter string");
        }
        return bloodBankRepository.findByRatingRange(Double.parseDouble(numbers[0]), Double.parseDouble(numbers[1]), page);
    }

    @Override
    public Page<BloodBank> getBanksByName(String filter, Pageable page) throws Exception {
        return bloodBankRepository.findAllByName(filter.toLowerCase(), page);
    }

    @Override
    public Page<BloodBank> getBanksByAddress(String filter, Pageable page) throws Exception {
        return bloodBankRepository.findByAddress(filter.toLowerCase(), page);
    }

    public Optional<BloodBank> findByEmail(String email) {
        if(!bloodBankRepository.findByEmail(email).isPresent())
            throw new IllegalStateException("Bank with that kind of email doesn't exist!");
        return bloodBankRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Integer sendBlood(String bankEmail, String bloodType, int quantity) {
        if(checkForBlood(bankEmail, bloodType, quantity)){
            reduceBloodSupplies(bloodBankRepository.findByEmail(bankEmail).get(),bloodType, quantity);
            return quantity;
        }
        throw new UnsupportedOperationException("Can't send blood");
    }

    @Override
    public Boolean savePDF(String bankEmail, byte[] pdf) {
        LocalDateTime today = LocalDateTime.now();
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("ddMMyyyy_hhmm_"));
        try (FileOutputStream fos = new FileOutputStream(filePath + "/src/report_" + formattedDate + bankEmail +".pdf")) {
            fos.write(pdf);
            return true;
        }catch(Exception e){
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    @Override
    public boolean checkIfBloodSupplyAvailable(String bankEmail, ScheduledOrder order) {
        BloodBank bloodBank = bloodBankRepository.findByEmail(bankEmail).orElseThrow();
        if(bloodBank.getBlood().getAplus() < order.getAplus())
            return false;

        if(bloodBank.getBlood().getABplus() < order.getABplus())
            return false;

        if(bloodBank.getBlood().getBplus() < order.getBplus())
            return false;

        if(bloodBank.getBlood().getOplus() < order.getOplus())
            return false;

        if(bloodBank.getBlood().getAminus() < order.getAminus())
            return false;

        if(bloodBank.getBlood().getABminus() < order.getABminus())
            return false;

        if(bloodBank.getBlood().getBminus() < order.getBminus())
            return false;

        if(bloodBank.getBlood().getOminus() < order.getOminus())
            return false;

        return true;
    }

    @Override
    public void reduceBloodSupply(String bankEmail, ScheduledOrder order) throws Exception {
        if(!checkIfBloodSupplyAvailable(bankEmail, order)){
            throw new Exception("Cant reduce blood");
        }
        BloodBank bloodBank = bloodBankRepository.findByEmail(bankEmail).orElseThrow();
        bloodBank.getBlood().setAplus(bloodBank.getBlood().getAplus() - order.getAplus());
        bloodBank.getBlood().setABplus(bloodBank.getBlood().getABplus() - order.getABplus());
        bloodBank.getBlood().setBplus(bloodBank.getBlood().getBplus() - order.getBplus());
        bloodBank.getBlood().setOplus(bloodBank.getBlood().getOplus() - order.getOplus());
        bloodBank.getBlood().setAminus(bloodBank.getBlood().getAminus() - order.getAminus());
        bloodBank.getBlood().setABminus(bloodBank.getBlood().getABminus() - order.getABminus());
        bloodBank.getBlood().setBminus(bloodBank.getBlood().getBminus() - order.getBminus());
        bloodBank.getBlood().setOminus(bloodBank.getBlood().getOminus() - order.getOminus());
        bloodBankRepository.save(bloodBank);
    }

    @Transactional
    private void reduceBloodSupplies(BloodBank bank, String bloodType, int quantity){
        switch (bloodType) {
            case "Aplus":
                bank.getBlood().setAplus(bank.getBlood().getAplus() - quantity);
                break;
            case "ABplus":
                bank.getBlood().setABplus(bank.getBlood().getABplus() - quantity);
                break;
            case "Bplus":
                bank.getBlood().setBplus(bank.getBlood().getBplus() - quantity);
                break;
            case "Oplus":
                bank.getBlood().setOplus(bank.getBlood().getOplus() - quantity);
                break;
            case "Aminus":
                bank.getBlood().setAminus(bank.getBlood().getAminus() - quantity);
                break;
            case "ABminus":
                bank.getBlood().setABminus(bank.getBlood().getABminus() - quantity);
                break;
            case "Bminus":
                bank.getBlood().setBminus(bank.getBlood().getBminus() - quantity);
                break;
            case "Ominus":
                bank.getBlood().setOminus(bank.getBlood().getOminus() - quantity);
                break;
            default:
                throw new IllegalStateException("BloodType unrecognizable.");
        }
        bloodBankRepository.save(bank);
    }
    public List<BloodBankDTO> GetBanksAsDTO() throws Exception {
        List<BloodBankDTO> bankDTOS = modelMapper.map(bloodBankRepository.findAll(), new TypeToken<List<BloodBankDTO>>() {}.getType());
        return bankDTOS;
    }

    @Override
    public BloodBank Create(BloodBank entity) throws Exception {
        return null;
    }

    @Override
    public BloodBank Read(Long id) throws Exception {
        Optional<BloodBank> bloodBank = bloodBankRepository.findById(id);
        if(bloodBank.isPresent()){
            return bloodBank.get();
        } else {
            throw new EntityDoesntExistException(id);
        }
    }

    @Override
    public BloodBank Update(BloodBank entity) throws Exception {
        return bloodBankRepository.save(entity);
    }

    @Override
    public void Delete(BloodBank entity) throws Exception {

    }

    @Override
    public Iterable<BloodBank> GetAll() throws Exception {
        return null;
    }
}
