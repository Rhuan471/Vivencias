package com.exemplo.eventos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.EQUIPE2.entidade.Evento;
import com.example.demo.model.EQUIPE2.entidade.Perfil;
import com.exemplo.eventos.dto.PerfilDTO;
import com.exemplo.eventos.exception.ResourceNotFoundException;

import repository.EventoRepository;
import repository.PerfilRepository;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private EventoRepository eventoRepository; // Para favoritar eventos

    public List<PerfilDTO> getAllPerfis() {
        return perfilRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PerfilDTO getPerfilById(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o ID: " + id));
        return convertToDto(perfil);
    }

    @Transactional
    public PerfilDTO updatePerfil(Long id, PerfilDTO perfilDTO) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o ID: " + id));

        perfil.setNome(perfilDTO.getNome());
        perfil.setEmail(perfilDTO.getEmail());
        perfil.setBio(perfilDTO.getBio());
        // Não atualize a senha diretamente aqui; crie um endpoint separado para isso

        Perfil updatedPerfil = perfilRepository.save(perfil);
        return convertToDto(updatedPerfil);
    }

    // Endpoint de denúncia (simulação, você precisaria de uma lógica mais robusta)
    public void denunciarPerfil(Long idPerfilDenunciado, Long idPerfilDenunciante, String motivo) {
        Perfil perfilDenunciado = perfilRepository.findById(idPerfilDenunciado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil a ser denunciado não encontrado com o ID: " + idPerfilDenunciado));

        // Lógica de denúncia:
        // - Salvar a denúncia em um log/banco de dados
        // - Notificar administradores
        // - Aplicar regras de negócio (e.g., contador de denúncias para suspensão)
        System.out.println(String.format("Perfil %s denunciado por %s. Motivo: %s",
                perfilDenunciado.getNome(),
                (idPerfilDenunciante != null ? "Perfil ID: " + idPerfilDenunciante : "Anônimo"),
                motivo));
        // Em um ambiente real, você salvaria isso em um modelo de Denuncia
    }

    @Transactional
    public PerfilDTO favoritarEvento(Long idPerfil, Long idEvento) {
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o ID: " + idPerfil));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + idEvento));

        perfil.getEventosFavoritados().add(evento);
        evento.getPerfisFavoritaram().add(perfil); // Garante a consistência bidirecional

        Perfil updatedPerfil = perfilRepository.save(perfil);
        eventoRepository.save(evento); // Salva a alteração no evento também

        return convertToDto(updatedPerfil);
    }

    @Transactional
    public PerfilDTO desfavoritarEvento(Long idPerfil, Long idEvento) {
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado com o ID: " + idPerfil));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + idEvento));

        perfil.getEventosFavoritados().remove(evento);
        evento.getPerfisFavoritaram().remove(perfil);

        Perfil updatedPerfil = perfilRepository.save(perfil);
        eventoRepository.save(evento);

        return convertToDto(updatedPerfil);
    }


    private PerfilDTO convertToDto(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setNome(perfil.getNome());
        dto.setEmail(perfil.getEmail());
        dto.setBio(perfil.getBio());
        return dto;
    }

    private Perfil convertToEntity(PerfilDTO dto) {
        Perfil perfil = new Perfil();
        perfil.setId(dto.getId());
        perfil.setNome(dto.getNome());
        perfil.setEmail(dto.getEmail());
        perfil.setBio(dto.getBio());
        return perfil;
    }
}
