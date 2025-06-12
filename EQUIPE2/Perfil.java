package EQUIPE2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    

    public Perfil() {}

    public Perfil(String nome, String descricao, String dataNascimento) {
        this.nome = nome;
        this.descricao = descricao;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenunciaString() {
        String DenunciaString;
        return DenunciaString = "Denúncia de perfil: " + this.nome + " - " + this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
// Responsabilidades
// CRUD de Perfis e Eventos
// Filtros por categoria e palavras-chave
// Reaproveitamento de componentes
// Relacionamento entre criadores e recursos
// Modelos: Perfil, Evento, Categoria
// Endpoints

// Perfis
// GET /perfis
// GET /perfis/{id}
// PUT /perfis/{id}
// POST /perfis/{id}/denunciar
// POST /perfis/{id}/favoritar

// Eventos
// GET /eventos
// GET /eventos/{id}
// POST /eventos
// PUT /eventos/{id}
// DELETE /eventos/{id}
// GET /eventos/rascunhos
// GET /eventos/historico

// Inscrições
// POST /eventos/{id}/inscricoes
// DELETE /inscricoes/{id}
// GET /usuarios/{id}/inscricoes
// GET /eventos/{id}/ticket
// Categorias
// GET /categorias/atores
// GET /categorias/eventos
