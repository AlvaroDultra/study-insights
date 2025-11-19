package com.insights.cli.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insights.cli.service.ApiClient;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "stats",
        description = "Exibe estatísticas de estudo"
)
public class StatsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        ApiClient client = new ApiClient();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String response = client.get("/statistics/weekly");
            JsonNode stats = mapper.readTree(response);

            System.out.println("\n📊 ESTATÍSTICAS DA SEMANA");
            System.out.println("═══════════════════════════════");
            System.out.println("⏱️  Tempo total: " + stats.get("totalHours").asDouble() + " horas");
            System.out.println("📝 Total de sessões: " + stats.get("sessionsCount").asInt());
            System.out.println("🎯 Foco médio: " + stats.get("averageFocusScore").asDouble() + "/10");

            System.out.println("\n📚 POR DISCIPLINA");
            System.out.println("═══════════════════════════════");

            String bySubject = client.get("/statistics/by-subject");
            JsonNode subjects = mapper.readTree(bySubject);

            subjects.fields().forEachRemaining(entry -> {
                System.out.println("  • " + entry.getKey() + ": " + entry.getValue().asInt() + " min");
            });

            return 0;
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar estatísticas: " + e.getMessage());
            return 1;
        }
    }
}