"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { apiFetch } from "@/lib/api";

const formatCurrency = (value) => new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(Number(value) || 0);

const formatDate = (value) => {
  if (!value) return "Data não informada";
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? value : new Intl.DateTimeFormat("pt-BR", { dateStyle: "short", timeStyle: "short" }).format(date);
};

function CartIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-5 w-5" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><path d="M3 4h2l2.2 10.2a2 2 0 0 0 2 1.6h7.9a2 2 0 0 0 2-1.6L20.5 8H6.3" /><circle cx="9" cy="19" r="1.3" /><circle cx="18" cy="19" r="1.3" /></svg>;
}

function SearchIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round"><circle cx="11" cy="11" r="7" /><path d="m20 20-4-4" /></svg>;
}

export default function AdminVendasPage() {
  const [idProduto, setIdProduto] = useState("");
  const [quantidade, setQuantidade] = useState("");
  const [desconto, setDesconto] = useState("");
  const [vendas, setVendas] = useState([]);
  const [produtos, setProdutos] = useState([]);
  const [busca, setBusca] = useState("");
  const [ordem, setOrdem] = useState("recentes");
  const [carregando, setCarregando] = useState(true);
  const [salvando, setSalvando] = useState(false);
  const [confirmandoId, setConfirmandoId] = useState(null);
  const [mensagem, setMensagem] = useState(null);

  const carregar = useCallback(async () => {
    try {
      setCarregando(true);
      const [salesData, productsData] = await Promise.all([apiFetch("/api/vendas"), apiFetch("/api/produtos")]);
      setVendas(Array.isArray(salesData) ? salesData : []);
      setProdutos(Array.isArray(productsData) ? productsData : []);
    } catch (error) {
      console.error("Erro ao carregar vendas:", error);
      setMensagem({ tipo: "erro", texto: "Não foi possível carregar os dados do caixa." });
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect
    carregar();
  }, [carregar]);

  const produtoSelecionado = useMemo(() => produtos.find((produto) => Number(produto.codigo) === Number(idProduto)), [idProduto, produtos]);
  const totalEstimado = useMemo(() => {
    if (!produtoSelecionado || !quantidade) return 0;
    const subtotal = (Number(produtoSelecionado.preco) || 0) * Number(quantidade);
    return Math.max(0, subtotal - subtotal * (Number(desconto || 0) / 100));
  }, [desconto, produtoSelecionado, quantidade]);

  const resumo = useMemo(() => ({
    faturamento: vendas.reduce((total, venda) => total + (Number(venda.valorTotal) || 0), 0),
    itens: vendas.reduce((total, venda) => total + (Number(venda.quantidadeProdutos) || 0), 0),
  }), [vendas]);

  const filtradas = useMemo(() => {
    const termo = busca.trim().toLocaleLowerCase("pt-BR");
    const lista = vendas.filter((venda) => !termo || String(venda.id ?? "").includes(termo) || String(venda.dataVenda || "").toLocaleLowerCase("pt-BR").includes(termo));
    return lista.sort((a, b) => {
      if (ordem === "maior-total") return (Number(b.valorTotal) || 0) - (Number(a.valorTotal) || 0);
      if (ordem === "menor-total") return (Number(a.valorTotal) || 0) - (Number(b.valorTotal) || 0);
      return (Number(b.id) || 0) - (Number(a.id) || 0);
    });
  }, [busca, ordem, vendas]);

  const limpar = () => {
    setIdProduto("");
    setQuantidade("");
    setDesconto("");
    setMensagem(null);
  };

  const realizarVenda = async (event) => {
    event.preventDefault();
    setMensagem(null);
    try {
      setSalvando(true);
      await apiFetch("/api/vendas", {
        method: "POST",
        body: JSON.stringify({ idProduto: Number(idProduto), quantidade: Number(quantidade), desconto: Number(desconto || 0) }),
      });
      limpar();
      setMensagem({ tipo: "sucesso", texto: "Venda registrada com sucesso." });
      await carregar();
    } catch (error) {
      console.error("Erro ao realizar venda:", error);
      setMensagem({ tipo: "erro", texto: "A venda não foi registrada. Confira os dados informados." });
    } finally {
      setSalvando(false);
    }
  };

  const excluir = async (id) => {
    try {
      await apiFetch(`/api/vendas/${id}`, { method: "DELETE" });
      setConfirmandoId(null);
      setMensagem({ tipo: "sucesso", texto: "Venda removida do histórico." });
      await carregar();
    } catch (error) {
      console.error("Erro ao excluir venda:", error);
      setMensagem({ tipo: "erro", texto: "Não foi possível remover esta venda." });
    }
  };

  return (
    <div className="rise-in">
      <div className="flex flex-col gap-4 xl:flex-row xl:items-end xl:justify-between">
        <div><p className="eyebrow">Frente de caixa</p><h1 className="mt-2 text-4xl font-semibold tracking-[-0.045em] text-[#214f43]">Vendas</h1><p className="mt-3 max-w-2xl text-base leading-7 text-[#6d7b74]">Escolha o produto, informe a quantidade e confira o valor antes de finalizar.</p></div>
        <Link href="/admin/produtos" className="button-secondary self-start xl:self-auto">+ Cadastrar produto</Link>
      </div>

      <section className="mt-6 grid gap-4 sm:grid-cols-3">
        <div className="surface-card p-5"><span className="text-sm font-bold text-[#6f7d75]">Faturamento</span><strong className="font-display mt-2 block text-2xl font-semibold text-[#286858]">{carregando ? "—" : formatCurrency(resumo.faturamento)}</strong></div>
        <div className="surface-card p-5"><span className="text-sm font-bold text-[#6f7d75]">Vendas</span><strong className="font-display mt-2 block text-2xl font-semibold text-[#a66054]">{carregando ? "—" : vendas.length}</strong></div>
        <div className="surface-card p-5"><span className="text-sm font-bold text-[#6f7d75]">Itens vendidos</span><strong className="font-display mt-2 block text-2xl font-semibold text-[#80682e]">{carregando ? "—" : resumo.itens}</strong></div>
      </section>

      <div className="mt-6 grid items-start gap-6 xl:grid-cols-[0.74fr_1.26fr]">
        <section className="surface-card overflow-hidden">
          <div className="border-b border-[#dfd8cc] bg-[#fae8dd] px-6 py-6"><span className="grid h-10 w-10 place-items-center rounded-xl bg-white text-[#b56759]"><CartIcon /></span><h2 className="mt-5 text-2xl font-semibold text-[#244c41]">Nova venda</h2><p className="mt-1 text-sm text-[#756d68]">Os produtos cadastrados aparecem na seleção.</p></div>
          <form onSubmit={realizarVenda} className="space-y-5 p-6">
            <label className="block"><span className="mb-2 block text-sm font-bold text-[#37564c]">Produto</span><select value={idProduto} onChange={(event) => setIdProduto(event.target.value)} className="field" required disabled={!produtos.length}><option value="">{produtos.length ? "Selecione um produto" : "Cadastre um produto primeiro"}</option>{produtos.map((produto) => <option key={produto.codigo} value={produto.codigo}>#{produto.codigo} · {produto.nome} — {formatCurrency(produto.preco)}</option>)}</select></label>
            <div className="grid gap-4 sm:grid-cols-2">
              <label><span className="mb-2 block text-sm font-bold text-[#37564c]">Quantidade</span><input type="number" min="1" value={quantidade} onChange={(event) => setQuantidade(event.target.value)} className="field" placeholder="Ex.: 2" required /></label>
              <label><span className="mb-2 block text-sm font-bold text-[#37564c]">Desconto (%)</span><input type="number" min="0" max="100" step="0.01" value={desconto} onChange={(event) => setDesconto(event.target.value)} className="field" placeholder="0" /></label>
            </div>
            {produtoSelecionado && <div className="rounded-2xl border border-[#dfe7de] bg-[#f4f8f2] p-4 text-sm"><div className="flex justify-between gap-4"><span className="text-[#6d7b74]">Produto</span><strong className="truncate text-[#345248]">{produtoSelecionado.nome}</strong></div><div className="mt-2 flex justify-between gap-4"><span className="text-[#6d7b74]">Preço unitário</span><strong className="text-[#345248]">{formatCurrency(produtoSelecionado.preco)}</strong></div></div>}
            <div className="flex items-center justify-between rounded-2xl bg-[#e2f0e5] p-4"><span className="text-sm font-bold text-[#527066]">Total estimado</span><strong className="font-display text-xl font-semibold text-[#245f50]">{formatCurrency(totalEstimado)}</strong></div>
            <div className="grid grid-cols-2 gap-3"><button type="button" onClick={limpar} className="button-secondary">Limpar</button><button type="submit" disabled={salvando || !produtos.length} className="button-primary">{salvando ? "Registrando..." : "Finalizar venda"}</button></div>
            {mensagem && <p className={mensagem.tipo === "sucesso" ? "status-success" : "status-error"}>{mensagem.texto}</p>}
          </form>
        </section>

        <section className="surface-card overflow-hidden">
          <div className="border-b border-[#e2dbcf] p-5 sm:p-6">
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between"><div><h2 className="text-2xl font-semibold text-[#244c41]">Histórico do caixa</h2><p className="mt-1 text-sm text-[#748079]">{filtradas.length} registros exibidos</p></div><button type="button" onClick={carregar} className="button-secondary self-start sm:self-auto">↻ Atualizar</button></div>
            <div className="mt-5 grid gap-3 sm:grid-cols-[1fr_180px]"><label className="relative"><span className="sr-only">Buscar vendas</span><span className="pointer-events-none absolute inset-y-0 left-0 grid w-11 place-items-center text-[#708078]"><SearchIcon /></span><input type="search" value={busca} onChange={(event) => setBusca(event.target.value)} placeholder="Buscar por número ou data..." className="field field-search" /></label><select value={ordem} onChange={(event) => setOrdem(event.target.value)} className="field" aria-label="Ordenar vendas"><option value="recentes">Mais recentes</option><option value="maior-total">Maior total</option><option value="menor-total">Menor total</option></select></div>
          </div>
          {carregando ? <div className="space-y-3 p-6">{[1, 2, 3].map((item) => <div key={item} className="h-16 animate-pulse rounded-2xl bg-[#f0eee8]" />)}</div> : filtradas.length === 0 ? <div className="grid min-h-72 place-items-center p-8 text-center"><div><span className="mx-auto grid h-14 w-14 place-items-center rounded-2xl bg-[#fae8dd] text-[#a66054]">V</span><h3 className="mt-4 text-xl font-semibold text-[#294b41]">Nenhuma venda encontrada</h3><p className="mt-2 text-sm text-[#748079]">As vendas finalizadas aparecerão aqui.</p></div></div> : <div className="overflow-x-auto"><table className="w-full min-w-[700px] text-left"><thead><tr className="bg-[#fbf8f1] text-xs font-extrabold uppercase tracking-[0.08em] text-[#78847e]"><th className="px-5 py-3.5">Venda</th><th className="px-5 py-3.5">Itens</th><th className="px-5 py-3.5">Desconto</th><th className="px-5 py-3.5 text-right">Total</th><th className="px-5 py-3.5 text-right">Ação</th></tr></thead><tbody className="divide-y divide-[#ebe5db]">{filtradas.map((venda) => <tr key={venda.id} className="hover:bg-[#f4f8f2]"><td className="px-5 py-4"><strong className="block text-sm text-[#345248]">Venda #{venda.id}</strong><span className="text-xs text-[#89928d]">{formatDate(venda.dataVenda)}</span></td><td className="px-5 py-4 text-sm font-semibold text-[#65756d]">{venda.quantidadeProdutos || 0}</td><td className="px-5 py-4"><span className="rounded-full bg-[#f7edc8] px-2.5 py-1 text-xs font-bold text-[#80682e]">{Number(venda.desconto || 0).toFixed(2)}%</span></td><td className="px-5 py-4 text-right font-bold text-[#286858]">{formatCurrency(venda.valorTotal)}</td><td className="px-5 py-4 text-right">{confirmandoId === venda.id ? <span className="inline-flex gap-2"><button type="button" onClick={() => setConfirmandoId(null)} className="button-quiet">Cancelar</button><button type="button" onClick={() => excluir(venda.id)} className="rounded-xl bg-[#b85d52] px-3 py-2 text-xs font-bold text-white">Confirmar</button></span> : <button type="button" onClick={() => setConfirmandoId(venda.id)} className="button-quiet text-[#ad5a50] hover:bg-[#fff0eb]">Excluir</button>}</td></tr>)}</tbody></table></div>}
        </section>
      </div>
    </div>
  );
}
