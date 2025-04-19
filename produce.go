package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"math/rand"
	"net/http"
	"time"
)

type OrderItem struct {
	ProductID int    `json:"productId"`
	Quantity  int    `json:"quantity"`
	Price     string `json:"price"`
}

type CreateOrderDTO struct {
	CustomerName  string      `json:"customerName"`
	CustomerEmail string      `json:"customerEmail"`
	Items         []OrderItem `json:"items"`
}

var client = &http.Client{}

func randomOrder() CreateOrderDTO {
	names := []string{"João", "Maria", "Carlos", "Ana", "Fernanda", "Rafael"}
	sobrenomes := []string{"Silva", "Souza", "Oliveira", "Lima", "Costa", "Pereira"}

	name := fmt.Sprintf("%s %s", names[rand.Intn(len(names))], sobrenomes[rand.Intn(len(sobrenomes))])
	email := fmt.Sprintf("%s.%s%d@example.com", names[rand.Intn(len(names))], sobrenomes[rand.Intn(len(sobrenomes))], rand.Intn(10000))

	numItems := rand.Intn(3) + 1
	items := make([]OrderItem, numItems)
	for i := range items {
		items[i] = OrderItem{
			ProductID: rand.Intn(10) + 1,
			Quantity:  rand.Intn(5) + 1,
			Price:     fmt.Sprintf("%.2f", rand.Float64()*200),
		}
	}

	return CreateOrderDTO{
		CustomerName:  name,
		CustomerEmail: email,
		Items:         items,
	}
}

func sendOrder() {
	order := randomOrder()

	jsonData, err := json.Marshal(order)
	if err != nil {
		fmt.Println("Erro ao converter JSON:", err)
		return
	}

	req, err := http.NewRequest("POST", "http://localhost:9999/api/v1/order", bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("Erro ao criar requisição:", err)
		return
	}
	req.Header.Set("Content-Type", "application/json")

	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Erro ao enviar requisição:", err)
		return
	}
	defer resp.Body.Close()

	fmt.Println("Status Code:", resp.Status)
}

func main() {
	rand.Seed(time.Now().UnixNano())
	ticker := time.NewTicker(20 * time.Millisecond) // 50 req/s
	defer ticker.Stop()

	fmt.Println("Enviando pedidos... pressione Ctrl+C para parar.")

	for range ticker.C {
		go sendOrder()
	}
}