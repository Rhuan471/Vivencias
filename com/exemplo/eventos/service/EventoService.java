package com.exemplo.eventos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public List<EventoDTO> getAllEventos() {
        return eventoRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EventoDTO getEventoById(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + id));
        return convertToDto(evento);
    }

    @Transactional
    public EventoDTO createEvento(EventoDTO eventoDTO) {
        Perfil criador = perfilRepository.findById(eventoDTO.getCriadorId())
                .orElseThrow(() -> new ResourceNotFoundException("Criador não encontrado com o ID: " + eventoDTO.getCriadorId()));

        Evento evento = convertToEntity(eventoDTO);
        evento.setCriador(criador);
        evento.setStatus(StatusEvento.RASCUNHO); // Eventos novos começam como rascunho

        Evento savedEvento = eventoRepository.save(evento);
        return convertToDto(savedEvento);
    }

    @Transactional
    public EventoDTO updateEvento(Long id, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + id));

        // Atualizar apenas os campos permitidos
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setDataHoraInicio(eventoDTO.getDataHoraInicio());
        evento.setDataHoraFim(eventoDTO.getDataHoraFim());
        evento.setLocal(eventoDTO.getLocal());
        evento.setStatus(eventoDTO.getStatus()); // Permitir atualização de status

        // Se o criador for alterado, buscar e setar o novo criador
        if (eventoDTO.getCriadorId() != null && !eventoDTO.getCriadorId().equals(evento.getCriador().getId())) {
            Perfil novoCriador = perfilRepository.findById(eventoDTO.getCriadorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Novo criador não encontrado com o ID: " + eventoDTO.getCriadorId()));
            evento.setCriador(novoCriador);
        }

        Evento updatedEvento = eventoRepository.save(evento);
        return convertToDto(updatedEvento);
    }

    @Transactional
    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Evento não encontrado com o ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    public List<EventoDTO> getRascunhos() {
        return eventoRepository.findByStatus(StatusEvento.RASCUNHO).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> getHistoricoEventos() {
        // Retorna eventos que já passaram da data/hora de fim
        return eventoRepository.findByDataHoraFimBefore(LocalDateTime.now()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private EventoDTO convertToDto(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setDescricao(evento.getDescricao());
        dto.setDataHoraInicio(evento.getDataHoraInicio());
        dto.setDataHoraFim(evento.getDataHoraFim());
        dto.setLocal(evento.getLocal());
        dto.setStatus(evento.getStatus());
        if (evento.getCriador() != null) {
            dto.setCriadorId(evento.getCriador().getId());
            dto.setCriadorNome(evento.getCriador().getNome());
        }
        return dto;
    }

    private Evento convertToEntity(EventoDTO dto) {
        Evento evento = new Evento();
        // ID não é setado em create, é gerado pelo DB
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHoraInicio(dto.getDataHoraInicio());
        evento.setDataHoraFim(dto.getDataHoraFim());
        evento.setLocal(dto.getLocal());
        evento.setStatus(dto.getStatus()); // Será sobrescrito para RASCUNHO na criação
        return evento;
    }
}