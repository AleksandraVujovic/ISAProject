package com.example.BloodBank;

import com.example.BloodBank.model.Complaint;
import com.example.BloodBank.model.ComplaintStatus;
import com.example.BloodBank.service.ComplaintService;
import com.example.BloodBank.service.service_interface.IComplaintService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintTests {
    @Autowired
    private IComplaintService complaintService;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingScenario() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Complaint complaintToUpdate = complaintService.findById(1027L).get();// ocitan objekat sa id 1027
                complaintToUpdate.setComplaintStatus(ComplaintStatus.ANSWERED);// izmenjen ucitan objekat
                complaintToUpdate.setAnswer("U redu, promenicemo");// izmenjen ucitan objekat
                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                complaintService.save(complaintToUpdate);// bacice ObjectOptimisticLockingFailureException

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Complaint complaintToUpdate = complaintService.findById(1027L).get();// ocitan objekat sa id 1027
                complaintToUpdate.setComplaintStatus(ComplaintStatus.ANSWERED);// izmenjen ucitan objekat
                complaintToUpdate.setAnswer("U redu, promenicemo");// izmenjen ucitan objekat
                complaintService.save(complaintToUpdate);
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

    }

}
