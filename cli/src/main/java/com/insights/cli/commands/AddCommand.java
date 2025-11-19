package com.insights.cli.commands;

import com.insights.cli.service.ApiClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(
        name = "add",
        description = "Adiciona uma nova sessão de estudo"
)
public class AddCommand implements Callable<Integer> {

    @Option(names = {"-d", "--disciplina"}, required = true, description = "Nome da disciplina")
    private String subject;

    @Option(names = {"-t", "--topico"}, description = "Tópico estudado")
    private String topic;

    @Option(names = {"-m", "--minutos"}, required = true, description = "Tempo em minutos")
    private Integer minutes;

    @Option(names = {"-f", "--foco"}, required = true, description = "Nível de foco (1-10)")
    private Integer focusScore;

    @Override
    public Integer call() throws Exception {
        ApiClient client = new ApiClient();

        Map<String, Object> session = new HashMap<>();
        session.put("subject", subject);
        session.put("topic", topic);
        session.put("minutes", minutes);
        session.put("focusScore", focusScore);
        session.put("startTime", LocalDateTime.now().toString());

        try {
            String response = client.post("/sessions", session);
            System.out.println("✅ Sessão registrada com sucesso!");
            System.out.println("📚 Disciplina: " + subject);
            System.out.println("⏱️  Tempo: " + minutes + " minutos");
            System.out.println("🎯 Foco: " + focusScore + "/10");
            return 0;
        } catch (Exception e) {
            System.err.println("❌ Erro ao registrar sessão: " + e.getMessage());
            return 1;
        }
    }
}