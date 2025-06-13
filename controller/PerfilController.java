package com.exemplo.eventos.controller;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilDTO>> getAllPerfis() {
        List<PerfilDTO> perfis = perfilService.getAllPerfis();
        return ResponseEntity.ok(perfis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> getPerfilById(@PathVariable Long id) {
        PerfilDTO perfil = perfilService.getPerfilById(id);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilDTO> updatePerfil(@PathVariable Long id, @RequestBody PerfilDTO perfilDTO) {
        PerfilDTO updatedPerfil = perfilService.updatePerfil(id, perfilDTO);
        return ResponseEntity.ok(updatedPerfil);
    }

    @PostMapping("/{id}/denunciar")
    public ResponseEntity<Void> denunciarPerfil(@PathVariable Long id, @RequestBody DenunciaDTO denunciaDTO) {
        // Em um cenário real, você obteria o ID do perfil denunciante do contexto de segurança
        // Por simplicidade, estamos passando um ID fictício ou null aqui.
        perfilService.denunciarPerfil(id, denunciaDTO.getIdPerfilDenunciante(), denunciaDTO.getMotivo());
        return ResponseEntity.accepted().build(); // 202 Accepted
    }

    @PostMapping("/{id}/favoritar/{idEvento}")
    public ResponseEntity<PerfilDTO> favoritarEvento(@PathVariable Long id, @PathVariable Long idEvento) {
        PerfilDTO updatedPerfil = perfilService.favoritarEvento(id, idEvento);
        return ResponseEntity.ok(updatedPerfil);
    }

    @DeleteMapping("/{id}/favoritar/{idEvento}") // Use DELETE para "desfavoritar"
    public ResponseEntity<PerfilDTO> desfavoritarEvento(@PathVariable Long id, @PathVariable Long idEvento) {
        PerfilDTO updatedPerfil = perfilService.desfavoritarEvento(id, idEvento);
        return ResponseEntity.ok(updatedPerfil);
    }
}