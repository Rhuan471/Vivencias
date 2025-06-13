package com.example.demo.model.EQUIPE2.com.example.demo.model.EQUIPE2.entidade;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public StatusEvento getStatus() {
        return status;
    }

    public void setStatus(StatusEvento status) {
        this.status = status;
    }

    public Perfil getCriador() {
        return criador;
    }

    public void setCriador(Perfil criador) {
        this.criador = criador;
    }

    public Set<Perfil> getPerfisFavoritaram() {
        return perfisFavoritaram;
    }

    public void setPerfisFavoritaram(Set<Perfil> perfisFavoritaram) {
        this.perfisFavoritaram = perfisFavoritaram;
    }
}