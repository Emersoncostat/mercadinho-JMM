'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';

export default function ProdutosPage() {
    const [produtos, setProdutos] = useState([]);
    const [nome, setNome] = useState('');
    const [preco, setPreco] = useState('');
    const [carregando, setCarregando] = useState(false);

    const API_URL = 'http://localhost:8082/produtos';

    const carregarProdutos = async () => {
        try {
            setCarregando(true);
            const res = await fetch(API_URL);
            const data = await res.json();
            setProdutos(data);
        } catch (err) {
            console.error('Erro ao buscar produtos:', err);
        } finally {
            setCarregando(false);
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
                alert('Produto cadastrado com sucesso!');
                setNome('');
                setPreco('');
                carregarProdutos();
            }
        } catch (err) {
            console.error('Erro ao cadastrar:', err);
        }
    };

    return (
        <main className="min-h-screen bg-gray-50 p-4 md:p-8">
            <div className="max-w-3xl mx-auto space-y-6">

                {/* Botão de Retornar para a Tela Inicial */}
                <Link
                    href="/"
                    className="inline-flex items-center gap-2 text-sm font-semibold text-gray-600 hover:text-blue-600 transition-colors bg-white px-4 py-2 rounded-lg border border-gray-200 shadow-sm w-fit"
                >
                    &larr; Voltar para o Início
                </Link>

                {/* Cabeçalho da Página */}
                <div>
                    <h1 className="text-3xl font-bold text-gray-800">Gerenciamento de Produtos</h1>
                    <p className="text-gray-500 text-sm mt-1">Cadastre novos itens e acompanhe o estoque em tempo real.</p>
                </div>

                {/* Card do Formulário de Cadastro */}
                <section className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
                    <h2 className="text-xl font-semibold text-gray-800 mb-4">Novo Produto</h2>

                    <form onSubmit={cadastrarProduto} className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                        <div className="sm:col-span-2">
                            <label className="block text-xs font-semibold text-gray-600 uppercase mb-1">
                                Nome do Produto
                            </label>
                            <input
                                type="text"
                                placeholder="Ex: Arroz 5kg"
                                value={nome}
                                onChange={(e) => setNome(e.target.value)}
                                className="w-full border border-gray-300 rounded-lg p-2.5 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                required
                            />
                        </div>

                        <div>
                            <label className="block text-xs font-semibold text-gray-600 uppercase mb-1">
                                Preço (R$)
                            </label>
                            <input
                                type="number"
                                step="0.01"
                                placeholder="0,00"
                                value={preco}
                                onChange={(e) => setPreco(e.target.value)}
                                className="w-full border border-gray-300 rounded-lg p-2.5 text-gray-800 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                required
                            />
                        </div>

                        <div className="sm:col-span-3 flex justify-end">
                            <button
                                type="submit"
                                className="w-full sm:w-auto bg-blue-600 hover:bg-blue-700 text-white font-semibold px-6 py-2.5 rounded-lg transition-colors shadow-sm"
                            >
                                Cadastrar Produto
                            </button>
                        </div>
                    </form>
                </section>

                {/* Card da Tabela/Lista de Produtos */}
                <section className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
                    <div className="flex justify-between items-center mb-4">
                        <h2 className="text-xl font-semibold text-gray-800">Produtos Cadastrados</h2>
                        <span className="text-xs font-medium bg-blue-50 text-blue-700 px-2.5 py-1 rounded-full">
              {produtos.length} {produtos.length === 1 ? 'item' : 'itens'}
            </span>
                    </div>

                    {carregando ? (
                        <p className="text-gray-500 text-sm py-4 text-center">Carregando produtos...</p>
                    ) : produtos.length === 0 ? (
                        <p className="text-gray-400 text-sm py-4 text-center border border-dashed rounded-lg">
                            Nenhum produto cadastrado no momento.
                        </p>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="w-full text-left border-collapse">
                                <thead>
                                <tr className="border-b border-gray-200 text-xs font-semibold uppercase text-gray-500 bg-gray-50">
                                    <th className="p-3">#</th>
                                    <th className="p-3">Produto</th>
                                    <th className="p-3 text-right">Preço</th>
                                </tr>
                                </thead>
                                <tbody className="divide-y divide-gray-100 text-sm">
                                {produtos.map((p, index) => (
                                    <tr key={index} className="hover:bg-gray-50/50 transition-colors">
                                        <td className="p-3 text-gray-400">{index + 1}</td>
                                        <td className="p-3 font-medium text-gray-800">{p.nome || 'Sem nome'}</td>
                                        <td className="p-3 text-right font-semibold text-green-600">
                                            R$ {Number(p.preco || 0).toFixed(2)}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </section>

            </div>
        </main>
    );
}