package com.insights.cli.commands;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insights.cli.service.ApiClient;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(
        name = "insights",
        description = "Exibe insights e recomendações inteligentes"
)
public class InsightsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        ApiClient client = new ApiClient();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String response = client.get("/insights");
            JsonNode insights = mapper.readTree(response);

            System.out.println("\n🧠 INSIGHTS INTELIGENTES");
            System.out.println("═══════════════════════════════════════");

            System.out.println("\n📈 Score de Produtividade: " +
                    insights.get("productivityScore").asInt() + "/100");

            System.out.println("\n🏆 Disciplina mais estudada: " +
                    insights.get("mostStudiedSubject").asText());

            System.out.println("\n⚠️  Disciplina negligenciada: " +
                    insights.get("neglectedSubject").asText());

            JsonNode bestTime = insights.get("bestStudyTime");
            System.out.println("\n⏰ " + bestTime.get("recommendation").asText());

            System.out.println("\n💡 RECOMENDAÇÃO:");
            System.out.println("───────────────────────────────────────");
            String recommendation = insights.get("recommendation").asText();
            System.out.println(recommendation);

            return 0;
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar insights: " + e.getMessage());
            return 1;
        }
    }
}