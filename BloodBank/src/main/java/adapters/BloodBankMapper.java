package adapters;

import com.example.BloodBank.dto.BloodBankDTO;
import com.example.BloodBank.model.BloodBank;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.List;

public class BloodBankMapper {

    private static ModelMapper modelMapper;

    public BloodBankMapper(ModelMapper mapper) {
        modelMapper = mapper;
    }

    public static List<BloodBankDTO> toBloodBankDTOList(List<BloodBank> banks){
        return modelMapper.map(banks, new TypeToken<List<BloodBankDTO>>() {}.getType());
    }

    public static BloodBankDTO toBloodBankDTO(BloodBank bank){
        return modelMapper.map(bank, BloodBankDTO.class);
    }
}
