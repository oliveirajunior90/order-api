package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

type OrderItem struct {
	ProductID int     `json:"productId"`
	Quantity  int     `json:"quantity"`
	Price     string  `json:"price"`
}

type CreateOrderDTO struct {
	CustomerName  string      `json:"customerName"`
	CustomerEmail string      `json:"customerEmail"`
	Items        []OrderItem `json:"items"`
}

func sendOrder() {
	url := "http://localhost:8080/api/v1/order"

	order := CreateOrderDTO{
		CustomerName:  "João Silva",
		CustomerEmail: "joao.silva@example.com",
		Items: []OrderItem{
			{ProductID: 1, Quantity: 2, Price: "99.90"},
			{ProductID: 2, Quantity: 1, Price: "149.50"},
		},
	}

	jsonData, err := json.Marshal(order)
	if err != nil {
		fmt.Println("Erro ao converter JSON:", err)
		return
	}

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(jsonData))
	if err != nil {
		fmt.Println("Erro ao criar requisição:", err)
		return
	}
	req.Header.Set("Content-Type", "application/json")

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Erro ao enviar requisição:", err)
		return
	}
	defer resp.Body.Close()

	fmt.Println("Status Code:", resp.Status)
}

func main() {
	sendOrder()
}