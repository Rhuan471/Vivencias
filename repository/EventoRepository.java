package entidade.repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByStatus(StatusEvento status);
    List<Evento> findByCriadorId(Long criadorId);
    List<Evento> findByDataHoraFimBefore(java.time.LocalDateTime now);
}