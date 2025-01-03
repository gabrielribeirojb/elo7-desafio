# Clone o repositório: 
```bash
git clone https://github.com/seu-usuario/space-probe-api.git
```

Navegue até a pasta do projeto: 
```bash
cd space-probe-api
```

Compile e execute a aplicação: 
```bash
./mvnw spring-boot
```

# Endpoints
## 1. Criar um Planeta
```bash
URL: POST /v1/planets
```

```bash
Request Body: { "name": "Marte", "width": 5, "height": 5 }
```
```bash
Response: { "id": 1, "name": "Marte", "width": 5, "height": 5 }
```

## 2. Criar uma Sonda
```bash
URL: POST /v1/probes
```
```bash
Request Body: { "name": "Sonda 1", "x": 1, "y": 2, "direction": "NORTH", "planetId": 1 }
```
```bash
Response: { "id": 1, "name": "Sonda 1", "x": 1, "y": 2, "direction": "NORTH", "planetId": 1 }
```

## 3. Movimentar uma Sonda
```bash
URL: POST /v1/probes/{id}/move
```
```bash
Request Body (Text Plain): LMLMLMLMM
```
```bash
Response (Quando movimentação é bem-sucedida): { "id": 1, "name": "Sonda 1", "x": 1, "y": 3, "direction": "NORTH", "planetId": 1 }
```

## 4. Erros de Colisão
Cenário: Quando uma sonda tenta se mover para a posição de outra sonda existente.
Exemplo:
```bash
Request: POST /v1/probes/{probeId}/move 
```
```bash
Body: M
```
```bash
Response: { "timestamp": "2024-10-10T15:37:45.253+00:00", "status": 409, "error": "Conflict", "message": "Colisão detectada com outra sonda", "path": "/v1/probes/1/move" }
```

# Erros Comuns
## Planeta Não Encontrado
```bash
Mensagem: "Planeta com ID {id} não encontrado"
Status: 404 Not Found
```

## Colisão Detectada

```bash
Mensagem: "Colisão detectada com outra sonda"
Status: 409 Conflict
```

## Movimento Fora dos Limites do Planeta
```bash
Mensagem: "Movimento fora dos limites do planeta"
Status: 400 Bad Request
```

# Testes
Os testes podem ser executados com o Maven:
```bash
./mvnw test
```
