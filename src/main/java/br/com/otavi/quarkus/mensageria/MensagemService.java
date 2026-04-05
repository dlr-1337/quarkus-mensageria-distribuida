package br.com.otavi.quarkus.mensageria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MensagemService {

    private final CopyOnWriteArrayList<Mensagem> mensagens = new CopyOnWriteArrayList<>();
    private final AtomicLong sequencia = new AtomicLong();

    public List<Mensagem> listar() {
        return List.copyOf(mensagens);
    }

    public Optional<Mensagem> buscarPorId(Long id) {
        return mensagens.stream()
                .filter(mensagem -> mensagem.getId().equals(id))
                .findFirst();
    }

    public Mensagem criar(Mensagem mensagemRecebida) {
        Mensagem mensagemSalva = new Mensagem(
                sequencia.incrementAndGet(),
                mensagemRecebida.getRemetente(),
                mensagemRecebida.getConteudo(),
                LocalDateTime.now());

        mensagens.add(mensagemSalva);
        return mensagemSalva;
    }

    public Optional<Mensagem> remover(Long id) {
        Optional<Mensagem> mensagemEncontrada = buscarPorId(id);
        mensagemEncontrada.ifPresent(mensagens::remove);
        return mensagemEncontrada;
    }

    void limpar() {
        mensagens.clear();
        sequencia.set(0);
    }
}
