package com.example.BloodBank;

import com.example.BloodBank.dto.appointmentDTOs.BookAppointmentDTO;
import com.example.BloodBank.model.Appointment;
import com.example.BloodBank.model.Customer;
import com.example.BloodBank.service.AppointmentService;
import com.example.BloodBank.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BloodBankApplicationTests {

	@Autowired
	private AppointmentService appointmentService;

	@Before
	public void setUp() throws Exception {
		/*Customer customer = new Customer(1, "saddas", "sadasddas", "sdfsad", "dassdad", "halid@gmail.com")
		appointmentService.Create(new Appointment(LocalDate.now(), LocalTime.now(), LocalTime.now().plusMinutes(1), ));*/
	}

	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void contextLoads() throws Throwable {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Future<?> future1 = executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 1");// izmenjen ucitan objekat
				Appointment appointment = null;
				try {
					appointment = appointmentService.Read(1031L);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
				try {
					System.out.println("Startovan Thread 1 da radi");
					appointment.setEndTime(Time.valueOf(LocalTime.now()));
					System.out.println(appointmentService.Update(appointment).toString());
					//appointmentService.CancelAppointment(new BookAppointmentDTO(1031, 1023));// bacice ObjectOptimisticLockingFailureException
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		executor.submit(new Runnable() {

			@Override
			public void run() {
				System.out.println("Startovan Thread 2");
				try {
					Appointment appointment = appointmentService.Read(1031L);
					appointment.setEndTime(Time.valueOf(LocalTime.now()));
					System.out.println(appointmentService.Update(appointment).toString());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		try {
			future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
		} catch (ExecutionException e) {
			System.out.println("Exception from thread " + e.getCause().getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
			throw e.getCause().getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

}
