package br.com.microservices.microservices.sendemail.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.sendemail.interfaces.ViewerRepository;
import br.com.microservices.microservices.sendemail.models.ViewerDTO;
import br.com.microservices.microservices.sendemail.models.ViewerModel;

@Service
public class ViewerService {

    @Autowired
    private ViewerRepository viewerRepository;

    public void saveViewerAccess(ViewerDTO dto) {
        Optional<ViewerModel> existingViewer = viewerRepository.findByIp(dto.getIp());

        if (existingViewer.isEmpty()) {
            ViewerModel viewer = new ViewerModel();
            viewer.setIp(dto.getIp());
            viewer.setUserAgent(dto.getUserAgent());
            viewer.setAccessTime(LocalDateTime.now());
            viewerRepository.save(viewer);
        } else {
            System.out.println("IP já registrado: " + dto.getIp());
        }
    }

    public long countViewerAccess() {
        return viewerRepository.count();
    }
}
