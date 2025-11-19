# 🎓 Study Insights

Microserviço de análise de hábitos de estudo com API REST + CLI inteligente.

## 📋 Sobre o Projeto

Study Insights é um sistema completo para análise e acompanhamento de sessões de estudo, oferecendo:

- 📊 **Estatísticas detalhadas** por disciplina e período
- 🧠 **Insights inteligentes** sobre produtividade
- ⏰ **Detecção do melhor horário** de estudo
- 🎯 **Score de produtividade** personalizado
- ⚠️ **Alertas** de disciplinas negligenciadas
- 💻 **CLI profissional** para uso no terminal

## 🚀 Tecnologias

### Backend (API REST)
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven

### CLI
- Java 21
- Picocli
- Jackson (JSON)
- HttpClient

## 📦 Instalação

### Pré-requisitos

- Java 21+
- PostgreSQL 12+
- Maven 3.8+

### 1. Clone o repositório
```bash
git clone https://github.com/AlvaroDultra/study-insights.git
cd study-insights
```

### 2. Configure o banco de dados
```bash
# Crie o banco
psql -U postgres
CREATE DATABASE study_insights;
\q
```

### 3. Configure as variáveis de ambiente
```bash
# Copie o arquivo de exemplo
cp .env.example .env

# Edite com suas credenciais
nano .env
```

### 4. Execute a API
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

### 5. Compile a CLI
```bash
cd cli
mvn clean package
```

## 💻 Uso da CLI

### Adicionar sessão de estudo
```bash
java -jar cli/target/study-cli-1.0.0-jar-with-dependencies.jar add \
  -d "Direito Penal" \
  -t "Crimes contra a vida" \
  -m 60 \
  -f 8
```

### Ver estatísticas
```bash
java -jar cli/target/study-cli-1.0.0-jar-with-dependencies.jar stats
```

### Ver insights
```bash
java -jar cli/target/study-cli-1.0.0-jar-with-dependencies.jar insights
```

## 🔌 Endpoints da API

### Sessões de Estudo

- `POST /api/sessions` - Criar sessão
- `GET /api/sessions` - Listar todas
- `GET /api/sessions/{id}` - Buscar por ID

### Estatísticas

- `GET /api/statistics/weekly` - Estatísticas semanais
- `GET /api/statistics/by-subject` - Tempo por disciplina
- `GET /api/statistics/best-time` - Melhor horário de estudo

### Insights

- `GET /api/insights` - Insights e recomendações completas

## 📊 Exemplo de Resposta - Insights
```json
{
  "productivityScore": 62,
  "mostStudiedSubject": "Direito Penal",
  "neglectedSubject": "Direito Civil",
  "bestStudyTime": {
    "bestHour": 14,
    "recommendation": "Seu melhor horário de estudo é entre 14h e 15h"
  },
  "weeklyStats": {
    "totalHours": 2.5,
    "averageFocusScore": 8.7,
    "sessionsCount": 3
  }
}
```

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## 📝 Licença

Este projeto está sob a licença MIT.

## 👤 Autor

**Alvaro Dultra**

- GitHub: [@AlvaroDultra](https://github.com/AlvaroDultra)
