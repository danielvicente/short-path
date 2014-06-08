## Introdução e Arquitetura

O projeto consiste em um "novo sistema" de logística que visa obter sempre a rota com o menor custo para a entrega de mercadorias.

Considerando-se que o cálculo da rota com o menor custo pode ser encarado como uma especialização do "Problema do caminho mínimo", cujo objetivo é determinar o menor custo para a travessia de um grafo entre dois vértices, e que as informações devem ser persistidas para que elas não se percam entre os deployments da aplicação, irei utilizar o banco de dados orientado a grafos Neo4j.

A aplicação foi dividida em diferentes camadas buscando um fraco acoplamento, onde cada uma depende apenas das camadas inferiores, aumentando a manutenibilidade do código. Além disso, a utilização do Spring Framework para a inversão de controle (IoC) e injeção de dependência (DI) torna-se interessante e aumenta a flexibilidade e a testabilidade da aplicação. 

As funcionalidades do sistema foram expostas como APIs REST através do framework Spring MVC. Além disso, uma aplicação web de exemplo utilizando o Dojo Toolkit foi criada para exemplificar a utilização da API desenvolvida. 

Podemos ainda utilizar balanceadores de carga e clusters para prover a alta disponibilidade e a tolerância a falhas ao sistema, ou ainda expor tudo em computação em nuvem.

Um [Diagrama de Componentes](https://docs.google.com/file/d/0B58PmdFBvQq9T1ctLVpRbWlCd2s/) simples da aplicação.

Um [Diagrama de Classes](https://docs.google.com/file/d/0B58PmdFBvQq9amY0R29HSjdhVFk/) da aplicação.

## Pré-Requisitos

- Java Runtime Edition 7

- Apache Tomcat 7

## Instalação da aplicação

Para instalar a aplicação, basta realizar o deploy do módulo em um Servidor TomCat e acessar a url local "http://localhost:8080/short-path".

## APIs REST

#### POST /short-path/services/shipping/logisticsNetwork/{network-name}

Cria ou atualiza as informações da malha logística especificada.

O formato de malha logística adotado é bastante simples, onde cada linha representa uma rota no formato descrito abaixo:

**origem** **destino** **distância**

```
A B 10
B D 15
A C 20
C D 30
B E 50
D E 30
```

*Cada rota será considerada como de sentido único entre a origem e o destino. Desta forma, no exemplo acima embora exista uma rota de A para B não há nenhuma rota de B para A.*
*É possível alterar a malha, bem como qualquer parâmetro do aplicativo.*

##### Requisição

Na URI da requisição deve se informar o nome da malha logísica que será criada ou atualizada e no corpo da mensagem, os dados referentes a malha logística.

**Representações aceitáveis**

- text/plain

Exemplo

```
POST /short-path/services/shipping/logisticsNetwork/Sample HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:28.0) Gecko/20100101 Firefox/28.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-us,en;q=0.8,pt-br;q=0.5,pt;q=0.3
Accept-Encoding: gzip, deflate
Content-Type: text/plain;charset=UTF-8
X-Requested-With: XMLHttpRequest
Referer: http://localhost:8080/short-path/
Content-Length: 41
Connection: keep-alive
Pragma: no-cache
Cache-Control: no-cache

A B 10
B D 15
A C 20
C D 30
B E 50
D E 30
```

#### Respostas

- 200

	Caso a requisição tenha sido processada com sucesso.

- 400

	Caso algum parâmetro inválido tenha sido fornecido.

- 500

	Caso tenha ocorrido algum erro durante o processamento da requisição.

#### GET /short-path/services/shipping/shippingDetails/{origin}/{destination}?vehicleMileage={vehicleMileage}&fuelPrice={fuelPrice}

Calcula a rota com o menor custo entre um ponto de origem e um ponto de destino, levando em consideração a autonomia do veículo e o preço do combustível informado.

##### Requisições

Na URI da requisição deve se informar:

A origem e o destino da rota em questão.

A autonomia do veículo e o preço do combustível devem ser incluídos como parâmetros de consulta da requisição.

Exemplo:

```
GET /short-path/services/shipping/shippingDetails/A/D?vehicleMileage=10&fuelPrice=2.5 HTTP/1.1
Host: localhost:8080
User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:28.0) Gecko/20100101 Firefox/28.0
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
Accept-Language: en-us,en;q=0.8,pt-br;q=0.5,pt;q=0.3
Accept-Encoding: gzip, deflate
Content-Type: application/x-www-form-urlencoded
X-Requested-With: XMLHttpRequest
Referer: http://localhost:8080/short-path/
Connection: keep-alive
```

#### Respostas

- 200 - application/json

	Caso a requisição tenha sido processada com sucesso. No corpo da mensagem será retornada uma representação em JSON da melhor rota encontrada.

Exemplo:

```
	{
		"shippingRate":6.25,
		"shippingRoute": {
			"origin":"A",
			"destination":"D",
			"legs":[
				{
					"origin":"A",
					"destination":"B",
					"distance":10.0
				},
				{
					"origin":"B",
					"destination":"D",
					"distance":15.0
				}
			],
			"length":25.0
		}
	}
```

- 204

	Caso não exista nenhuma rota entre a origem e o destino.

- 400

	Caso algum parâmetro inválido tenha sido fornecido.

- 500

	Caso tenha ocorrido algum erro durante o processamento da requisição.

## Documentações

A documentação das APIs Java do projeto está disponível em [Short Path API](https://docs.google.com/file/d/0B58PmdFBvQq9ajZpR1RwR2F3anM/) para download.

