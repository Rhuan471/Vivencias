package entidade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String local;
    @Enumerated(EnumType.STRING)
    private StatusEvento status; // RASCUNHO, PUBLICADO, CANCELADO, CONCLUIDO

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id")
    private Perfil criador;

    @ManyToMany(mappedBy = "eventosFavoritados")
    private Set<Perfil> perfisFavoritaram = new HashSet<>();

    public enum StatusEvento {
        RASCUNHO, PUBLICADO, CANCELADO, CONCLUIDO
    }
}