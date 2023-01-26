package adapters;

import com.example.BloodBank.dto.complaintDTOs.ComplaintDTO;
import com.example.BloodBank.model.Complaint;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ComplaintMapper {
    private static ModelMapper modelMapper;

    public ComplaintMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public static ComplaintDTO toDTO(Complaint complaint){
        ComplaintDTO complaintDTO = modelMapper.map(complaint, ComplaintDTO.class);
        String type = complaintDTO.getComplaintType();
        complaintDTO.setComplaintType(type.substring(0, 1) + type.substring(1).toLowerCase());
        complaintDTO.setCustomerName(complaint.getCustomer().getFirstName() + " "+ complaint.getCustomer().getLastName());
        if(complaint.getHeadAdmin() == null)
            return complaintDTO;
        complaintDTO.setHeadAdminId(complaint.getHeadAdmin().getId());
        return complaintDTO;
    }

    public static Complaint fromDTO(ComplaintDTO complaintDTO){
        complaintDTO.setComplaintType(complaintDTO.getComplaintType().toUpperCase());
        Complaint complaint = modelMapper.map(complaintDTO, Complaint.class);
        return complaint;
    }

    public static List<ComplaintDTO> toDTOList(List<Complaint> complaints){
        try{
            ArrayList<ComplaintDTO> complaintsDTO = modelMapper.map(complaints, new TypeToken<List<ComplaintDTO>>() {}.getType());
            for(ComplaintDTO complaintDTO : complaintsDTO){
                String type = complaintDTO.getComplaintType();
                complaintDTO.setComplaintType(type.substring(0, 1) + type.substring(1).toLowerCase());
            }
            return complaintsDTO;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}