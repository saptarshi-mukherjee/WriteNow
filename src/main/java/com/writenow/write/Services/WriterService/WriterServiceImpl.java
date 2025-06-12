package com.writenow.write.Services.WriterService;

import com.writenow.write.DTO.ResponseDto.WriterResponseDto;
import com.writenow.write.Models.EmailStatus;
import com.writenow.write.Models.Writer;
import com.writenow.write.Projections.WriterProjection;
import com.writenow.write.Repositories.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;


    @Override
    public String addWriter(String fullName, String email) {
        Writer writer=new Writer();
        writer.setFullName(fullName);
        writer.setEmail(email);
        writerRepository.save(writer);
        return "new writer added";
    }

    @Override
    public WriterResponseDto getWriterByName(String fullName) {
        List<WriterProjection> writerProjections=writerRepository.fetchWriterByName(fullName);
        WriterResponseDto response=new WriterResponseDto();
        response.setFulName(writerProjections.getFirst().getFull_name());
        response.setEmail(writerProjections.getFirst().getEmail());
        response.setId(writerProjections.getFirst().getId());
        response.setUrl(writerProjections.getFirst().getUrl());
        response.setEmailStatus(EmailStatus.values()[writerProjections.getFirst().getStatus()].toString());
        return response;
    }
}
