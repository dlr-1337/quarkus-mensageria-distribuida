package br.com.otavi.quarkus.mensageria;

import java.time.LocalDateTime;

public class Mensagem {

    private Long id;
    private String remetente;
    private String conteudo;
    private LocalDateTime timestamp;

    public Mensagem() {
    }

    public Mensagem(Long id, String remetente, String conteudo, LocalDateTime timestamp) {
        this.id = id;
        this.remetente = remetente;
        this.conteudo = conteudo;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
