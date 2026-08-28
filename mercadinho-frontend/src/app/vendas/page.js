"use client";

import { useEffect, useState } from "react";
import Link from "next/link";

export default function VendasPage() {
    const [idProduto, setIdProduto] = useState("");
    const [quantidade, setQuantidade] = useState("");
    const [desconto, setDesconto] = useState("");

    const [vendas, setVendas] = useState([]);
    const [carregando, setCarregando] = useState(false);

    const API_URL = "http://localhost:8082/api/vendas";

    const carregarVendas = async () => {
        try {
            const res = await fetch(API_URL);

            if (!res.ok) {
                throw new Error("Erro ao buscar vendas");
            }

            const data = await res.json();
            setVendas(data);
        } catch (err) {
            console.error("Erro ao buscar vendas:", err);
        }
    };

    useEffect(() => {
        carregarVendas();
    }, []);

    const realizarVenda = async (e) => {
        e.preventDefault();

        try {
            setCarregando(true);

            const res = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    idProduto: parseInt(idProduto),
                    quantidade: parseInt(quantidade),
                    desconto: parseFloat(desconto || 0),
                }),
            });

            if (!res.ok) {
                throw new Error("Não foi possível realizar a venda");
            }

            alert("Venda realizada com sucesso!");

            setIdProduto("");
            setQuantidade("");
            setDesconto("");

            carregarVendas();

        } catch (err) {
            console.error("Erro ao realizar venda:", err);
            alert("Erro ao realizar venda. Verifique se o produto existe e se o backend está funcionando.");
        } finally {
            setCarregando(false);
        }
    };

    return (
        <main className="min-h-screen p-8 bg-gray-50">
            <div className="max-w-4xl mx-auto">

                <Link
                    href="/"
                    className="inline-block mb-6 px-4 py-2 bg-white rounded-lg shadow border border-gray-200"
                >
                    ← Voltar para o Início
                </Link>

                <h1 className="text-3xl font-bold text-gray-800 mb-2">
                    Caixa / Vendas
                </h1>

                <p className="text-gray-600 mb-8">
                    Registre novas vendas e consulte o histórico.
                </p>

                <div className="bg-white rounded-lg shadow border border-gray-200 p-6 mb-6">
                    <h2 className="text-xl font-semibold text-gray-800 mb-6">
                        Nova Venda
                    </h2>

                    <form onSubmit={realizarVenda}>

                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    ID do Produto
                                </label>

                                <input
                                    type="number"
                                    value={idProduto}
                                    onChange={(e) => setIdProduto(e.target.value)}
                                    placeholder="Ex: 1"
                                    min="1"
                                    required
                                    className="w-full p-3 border border-gray-300 rounded-lg"
                                />
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    Quantidade
                                </label>

                                <input
                                    type="number"
                                    value={quantidade}
                                    onChange={(e) => setQuantidade(e.target.value)}
                                    placeholder="Ex: 2"
                                    min="1"
                                    required
                                    className="w-full p-3 border border-gray-300 rounded-lg"
                                />
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-2">
                                    Desconto (%)
                                </label>

                                <input
                                    type="number"
                                    step="0.01"
                                    value={desconto}
                                    onChange={(e) => setDesconto(e.target.value)}
                                    placeholder="0,00"
                                    min="0"
                                    className="w-full p-3 border border-gray-300 rounded-lg"
                                />
                            </div>

                        </div>

                        <button
                            type="submit"
                            disabled={carregando}
                            className="mt-6 px-6 py-3 bg-green-600 text-white font-semibold rounded-lg hover:bg-green-700 disabled:bg-gray-400"
                        >
                            {carregando ? "Realizando..." : "Realizar Venda"}
                        </button>

                    </form>
                </div>

                <div className="bg-white rounded-lg shadow border border-gray-200 p-6">

                    <div className="flex justify-between items-center mb-4">
                        <h2 className="text-xl font-semibold text-gray-800">
                            Histórico de Vendas
                        </h2>

                        <span className="text-xs font-medium bg-green-50 text-green-700 px-2.5 py-1 rounded-full">
                            {vendas.length} {vendas.length === 1 ? "venda" : "vendas"}
                        </span>
                    </div>

                    {vendas.length === 0 ? (
                        <p className="text-gray-400 text-sm py-4 text-center border border-dashed rounded-lg">
                            Nenhuma venda registrada no momento.
                        </p>
                    ) : (
                        <div className="overflow-x-auto">

                            <table className="w-full text-left">
                                <thead>
                                <tr className="border-b border-gray-200 text-xs font-semibold uppercase text-gray-500 bg-gray-50">
                                    <th className="p-3">ID</th>
                                    <th className="p-3">Data</th>
                                    <th className="p-3">Quantidade</th>
                                    <th className="p-3">Desconto</th>
                                    <th className="p-3 text-right">Total</th>
                                </tr>
                                </thead>

                                <tbody className="divide-y divide-gray-100 text-sm">
                                {vendas.map((venda) => (
                                    <tr key={venda.id}>
                                        <td className="p-3 text-gray-800">
                                            {venda.id}
                                        </td>

                                        <td className="p-3 text-gray-600">
                                            {venda.dataVenda}
                                        </td>

                                        <td className="p-3 text-gray-600">
                                            {venda.quantidadeProdutos}
                                        </td>

                                        <td className="p-3 text-gray-600">
                                            R$ {Number(venda.desconto || 0).toFixed(2)}
                                        </td>

                                        <td className="p-3 text-right font-semibold text-green-600">
                                            R$ {Number(venda.valorTotal || 0).toFixed(2)}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>

                        </div>
                    )}

                </div>

            </div>
        </main>
    );
}