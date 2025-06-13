package entidade.repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    // Métodos de busca customizados, se necessário
}