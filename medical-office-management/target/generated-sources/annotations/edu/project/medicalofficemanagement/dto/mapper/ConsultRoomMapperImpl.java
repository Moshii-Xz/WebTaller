package edu.project.medicalofficemanagement.dto.mapper;

import edu.project.medicalofficemanagement.dto.ConsultRoomDTO;
import edu.project.medicalofficemanagement.model.ConsultRoom;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-24T23:50:12-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class ConsultRoomMapperImpl implements ConsultRoomMapper {

    @Override
    public ConsultRoomDTO toDto(ConsultRoom consultRoom) {
        if ( consultRoom == null ) {
            return null;
        }

        ConsultRoomDTO consultRoomDTO = new ConsultRoomDTO();

        return consultRoomDTO;
    }

    @Override
    public ConsultRoom toEntity(ConsultRoomDTO consultRoomDTO) {
        if ( consultRoomDTO == null ) {
            return null;
        }

        ConsultRoom consultRoom = new ConsultRoom();

        return consultRoom;
    }
}
