'use client';

import { useState, useEffect } from 'react';

export default function Home() {
  const [produtos, setProdutos] = useState([]);
  const [nome, setNome] = useState('');
  const [preco, setPreco] = useState('');

  const API_URL = 'http://localhost:8080/produtos';

  const carregarProdutos = async () => {
    try {
      const res = await fetch(API_URL);
      const data = await res.json();
      setProdutos(data);
    } catch (err) {
      console.error('Erro ao buscar produtos:', err);
    }
  };

  useEffect(() => {
    carregarProdutos();
  }, []);


  const cadastrarProduto = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ nome, preco: parseFloat(preco) }),
      });

      if (res.ok) {
        alert('Produto cadastrado!');
        setNome('');
        setPreco('');
        carregarProdutos();
      }
    } catch (err) {
      console.error('Erro ao cadastrar:', err);
    }
  };

  return (
    <main className="p-8 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Cadastro de Produtos</h1>

      <form onSubmit={cadastrarProduto} className="flex flex-col gap-3 mb-6">
        <input
          type="text"
          placeholder="Nome do produto"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          className="border p-2 rounded text-black"
          required
        />
        <input
          type="number"
          step="0.01"
          placeholder="Preço"
          value={preco}
          onChange={(e) => setPreco(e.target.value)}
          className="border p-2 rounded text-black"
          required
        />
        <button type="submit" className="bg-blue-600 text-white p-2 rounded font-bold">
          Cadastrar
        </button>
      </form>

      <h2 className="text-xl font-bold mb-2">Lista de Produtos</h2>
      <ul className="list-disc pl-5">
        {produtos.map((p, index) => (
          <li key={index}>
            {p.nome || 'Item'} - R$ {p.preco || 0}
          </li>
        ))}
      </ul>
    </main>
  );
}