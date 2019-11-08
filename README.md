# Purple

Purple é um projeto de autorização de cadastro de conta e transações

## Installation

Seguir o tutorial de instalação do [docker](https://docs.docker.com/v17.09/engine/installation/#updates-and-patches)

Seguir o tutorial de instalação do [docker-compose](https://docs.docker.com/compose/install/)

### Docker

```bash
tar -zxvf purple.tar.gz
cd purple
```

```bash
ls

docker-compose.yaml  Dockerfile  entrypoint.sh  HELP.md  mvnw  mvnw.cmd  operations  pom.xml  purple.iml  README.md  src
```

### Maven

O Java 11 é necessário para utilizar o Maven

Seguir tutorial de instalação do [maven](https://www.baeldung.com/install-maven-on-windows-linux-mac)

```bash
tar -zxvf purple.tar.gz
cd purple
mvn clean install
```

```bash
ls

docker-compose.yaml  Dockerfile  entrypoint.sh  HELP.md  mvnw  mvnw.cmd  operations  pom.xml  purple.iml  README.md  src target
```


## Usage

```bash
cd purple
cat operations

{ "account": { "activeCard": true, "availableLimit": 1000 } }
{ "account": { "activeCard": true, "availableLimit": 200 } }
{ "transaction": { "merchant": "Habbib's", "amount": 30, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Avon", "amount": 60, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Ragazzo", "amount": 10, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Carrefour", "amount": 11, "time": "2019-02-13T11:04:00.000Z" } }
{ "transaction": { "merchant": "Havan", "amount": 11, "time": "2019-02-13T11:04:00.000Z" } }
{ "transaction": { "merchant": "Riachuelo", "amount": 11, "time": "2019-02-13T11:04:00.000Z" } }
{ "transaction": { "merchant": "KFC", "amount": 11, "time": "2019-02-13T11:04:00.000Z" } }
{ "transaction": { "merchant": "Brooxklin", "amount": 11, "time": "2019-02-13T11:04:00.000Z" } }
{ "transaction": { "merchant": "Outlet", "amount": 11, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Hashicorp", "amount": 11, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Forever 21", "amount": 11, "time": "2019-02-13T11:01:00.000Z" } }
{ "transaction": { "merchant": "Tayan", "amount": 11, "time": "2019-02-13T11:08:00.000Z" } }
{ "transaction": { "merchant": "Baked Potato", "amount": 11, "time": "2019-02-13T11:09:00.000Z" } }
```

Para modificar as transações realizadas, basta sobrescrever o arquivo operations

```bash
cd purple
echo { "account": { "activeCard": true, "availableLimit": 1000 } } > operations
cat operations

{ "account": { "activeCard": true, "availableLimit": 1000 } }
```

### Docker

Para executar a aplicação, basta chamar o docker-compose

```bash
cd purple
docker-compose -f docker-compose.yaml --build
```

### Maven

Para executar a aplicação, basta chamar o docker-compose

```bash
cd purple
docker-compose -f docker-compose-external-build.yaml --build
```

## Decisões Arquiteturais

* Spring Boot: Utilizado para Injeção de dependência e EventListener;
* Java: Trabalho atualmente com Java e me senti mais a vontade;
* Padrão de projeto Strategy: Encapsular regras de validação, sobre o mesmo contrato, mantendo a extensibilidade e a manutenabilidade;
* Padrão de projeto Cadeia de Responsabilidade: Estrutura condicional hierarquica, no melhor caso e no caso médio, evita algumas validações e processamentos desnecessários;
* Eventos: Com o EventListener, consigo processar mais rapidamenta o meu input, pois as chamadas de publisherEvent, são async;
* Docker e Docker-compose: Facilitar a execução do projeto;
* Implementação própria do meu Consumidor: A aplicação lê linha a linha do arquivo e em seguida cria um runnable e adiciona na fila, toda esta operação ocorre de maneira asincrona, no entanto, preciso garantir o FIFO, então instanciei uma BlockingQueue e controlo o consumo desta fila;
* Testes unitários: O foco dos testes ficou no Core da aplicação, evitando testes de get e set em modelos, só para aumentar a cobertura de código;
* Criei classes de serviço e de persistência, todas baseadas em interfaces, para garantir a manutenabilidade, para trocar a implementação, basta criar uma nova classe que implementa a interface e especificar na injeção de dependência;
  
### Implementações futuras

* Métodos de busca em coleções, a atual implementação no pior caso, pode percorrer totalmente as coleções, ou seja, a complexidade deste algoritmo é da ordem de O(n²)  
  As alternativas para resolver esse problema, é trocar o algoritmo de busca, utilizando uma árvore binária ou algo semelhante ao QuickSort.
* O input de dados na fila, é feito de maneira asincrona, pelos meus producers de eventos, o consumo da fila está asyncrono, mas não paralelizado, tomei essa decisão para manter a ordem de execução  
  A alternativa para melhorar essa implementação, seria criar um modelo de divisão de producers e consumers. Exemplo: Tenho 10 entradas, e 5 threads no meu pool, cada thread, seria responsável por duas entradas e cada consumer, seria responsável por ler essas entradas. Eles iriam compartilhar a mesma fila, fazendo com que eu mantesse a ordem de execução. **Obs: Manter a ordem de execução dessa forma, não garante 100%, pois dependendo das validações dessa transação, ela pode demorar mais do que outras, mas é um trade-off para melhorar o desempenho.**

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)