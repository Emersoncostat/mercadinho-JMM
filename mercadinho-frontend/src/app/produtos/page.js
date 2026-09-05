"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import Link from "next/link";
import AppHeader from "@/components/AppHeader";
import { API_BASE } from "@/lib/api";

const formatCurrency = (value) => new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(Number(value) || 0);

const formatDate = (value) => {
  if (!value) return null;
  const date = new Date(`${value}T12:00:00`);
  return Number.isNaN(date.getTime()) ? value : new Intl.DateTimeFormat("pt-BR").format(date);
};

function SearchIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round"><circle cx="11" cy="11" r="7" /><path d="m20 20-4-4" /></svg>;
}

export default function ProdutosPage() {
  const [produtos, setProdutos] = useState([]);
  const [busca, setBusca] = useState("");
  const [marca, setMarca] = useState("todas");
  const [ordem, setOrdem] = useState("nome");
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(false);

  const carregar = useCallback(async () => {
    try {
      setCarregando(true);
      const response = await fetch(`${API_BASE}/api/produtos`);
      if (!response.ok) throw new Error("Falha ao carregar produtos");
      const data = await response.json();
      setProdutos(Array.isArray(data) ? data : []);
      setErro(false);
    } catch (error) {
      console.error("Erro ao carregar catálogo:", error);
      setErro(true);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect
    carregar();
  }, [carregar]);

  const marcas = useMemo(() => [...new Set(produtos.map((produto) => produto.marca).filter(Boolean))].sort((a, b) => a.localeCompare(b, "pt-BR")), [produtos]);

  const filtrados = useMemo(() => {
    const termo = busca.trim().toLocaleLowerCase("pt-BR");
    const lista = produtos.filter((produto) => {
      const correspondeBusca = !termo || [produto.nome, produto.descricao, produto.marca, produto.codigoBarras].some((value) => String(value || "").toLocaleLowerCase("pt-BR").includes(termo));
      return correspondeBusca && (marca === "todas" || produto.marca === marca);
    });

    return lista.sort((a, b) => {
      if (ordem === "menor-preco") return (Number(a.preco) || 0) - (Number(b.preco) || 0);
      if (ordem === "maior-preco") return (Number(b.preco) || 0) - (Number(a.preco) || 0);
      return String(a.nome || "").localeCompare(String(b.nome || ""), "pt-BR");
    });
  }, [busca, marca, ordem, produtos]);

  const limparFiltros = () => {
    setBusca("");
    setMarca("todas");
    setOrdem("nome");
  };

  return (
    <div className="page-shell">
      <AppHeader active="produtos" />
      <main className="content-width py-8 sm:py-11">
        <section className="surface-card rise-in overflow-hidden">
          <div className="grid lg:grid-cols-[1.12fr_0.88fr]">
            <div className="px-6 py-9 sm:px-10 sm:py-12">
              <p className="eyebrow">Catálogo do Mercadinho JMM</p>
              <h1 className="mt-3 text-4xl font-semibold tracking-[-0.045em] text-[#214f43] sm:text-5xl">Encontre o que falta para o seu dia.</h1>
              <p className="mt-4 max-w-2xl text-base leading-7 text-[#6d7b74]">Consulte os produtos cadastrados, compare preços e veja os detalhes antes de passar no mercadinho.</p>
            </div>
            <div className="grid grid-cols-2 gap-px bg-[#d6e5db] lg:grid-cols-1">
              <div className="flex items-center gap-4 bg-[#e2f0e5] p-6"><strong className="font-display text-3xl font-semibold text-[#245f50]">{carregando ? "—" : produtos.length}</strong><span className="text-sm leading-5 text-[#62766d]">produtos<br />cadastrados</span></div>
              <div className="flex items-center gap-4 bg-[#f7edc8] p-6"><strong className="font-display text-3xl font-semibold text-[#80682e]">{carregando ? "—" : marcas.length}</strong><span className="text-sm leading-5 text-[#776a49]">marcas<br />diferentes</span></div>
            </div>
          </div>
        </section>

        <section className="surface-card mt-6 p-4 sm:p-5" aria-label="Filtros do catálogo">
          <div className="grid gap-3 md:grid-cols-[1fr_220px_200px_auto]">
            <label className="relative"><span className="sr-only">Buscar produto</span><span className="pointer-events-none absolute inset-y-0 left-0 grid w-11 place-items-center text-[#708078]"><SearchIcon /></span><input type="search" value={busca} onChange={(event) => setBusca(event.target.value)} placeholder="Buscar produto, marca ou descrição..." className="field field-search" /></label>
            <label><span className="sr-only">Filtrar por marca</span><select value={marca} onChange={(event) => setMarca(event.target.value)} className="field field-search"><option value="todas">Todas as marcas</option>{marcas.map((item) => <option key={item} value={item}>{item}</option>)}</select></label>
            <label><span className="sr-only">Ordenar produtos</span><select value={ordem} onChange={(event) => setOrdem(event.target.value)} className="field field-search"><option value="nome">Nome (A–Z)</option><option value="menor-preco">Menor preço</option><option value="maior-preco">Maior preço</option></select></label>
            <button type="button" onClick={limparFiltros} className="button-secondary">Limpar</button>
          </div>
        </section>

        <div className="mt-6 flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
          <div><p className="eyebrow">Nas prateleiras</p><h2 className="mt-2 text-3xl font-semibold text-[#244c41]">Produtos disponíveis</h2></div>
          <p className="text-sm text-[#748079]">{filtrados.length} {filtrados.length === 1 ? "resultado" : "resultados"}</p>
        </div>

        {erro ? (
          <div className="status-error mt-6 flex flex-wrap items-center justify-between gap-3"><span>Não foi possível carregar o catálogo. Confirme se o backend está funcionando.</span><button type="button" onClick={carregar} className="font-bold underline underline-offset-4">Tentar novamente</button></div>
        ) : carregando ? (
          <div className="mt-6 grid gap-5 sm:grid-cols-2 lg:grid-cols-3">{[1, 2, 3, 4, 5, 6].map((item) => <div key={item} className="h-64 animate-pulse rounded-3xl bg-white/75" />)}</div>
        ) : produtos.length === 0 ? (
          <div className="surface-card mt-6 grid min-h-72 place-items-center p-8 text-center"><div><span className="mx-auto grid h-14 w-14 place-items-center rounded-2xl bg-[#dcefe3] font-display text-xl text-[#286858]">P</span><h3 className="mt-4 text-2xl font-semibold text-[#294b41]">O catálogo está sendo preparado</h3><p className="mt-2 text-[#748079]">Em breve os produtos aparecerão por aqui.</p></div></div>
        ) : filtrados.length === 0 ? (
          <div className="surface-card mt-6 grid min-h-64 place-items-center p-8 text-center"><div><span className="mx-auto grid h-14 w-14 place-items-center rounded-2xl bg-[#fae8dd] text-[#a66054]"><SearchIcon /></span><h3 className="mt-4 text-2xl font-semibold text-[#294b41]">Nenhum produto encontrado</h3><p className="mt-2 text-[#748079]">Tente outro termo ou remova os filtros.</p><button type="button" onClick={limparFiltros} className="button-secondary mt-5">Ver todos os produtos</button></div></div>
        ) : (
          <div className="mt-6 grid gap-5 sm:grid-cols-2 lg:grid-cols-3">
            {filtrados.map((produto, index) => (
              <article key={produto.codigo ?? `${produto.nome}-${index}`} className="surface-card group flex min-h-64 flex-col overflow-hidden transition hover:-translate-y-1 hover:shadow-[0_24px_52px_rgba(36,73,61,0.1)]">
                <div className="flex items-start justify-between gap-4 bg-[#eaf4ec] p-5">
                  <span className="grid h-12 w-12 place-items-center rounded-2xl bg-white font-display text-xl font-semibold uppercase text-[#286858] shadow-[0_8px_18px_rgba(40,104,88,0.08)]">{(produto.nome || "P").charAt(0)}</span>
                  {produto.marca && <span className="max-w-[65%] truncate rounded-full bg-white/80 px-3 py-1.5 text-xs font-bold text-[#60746a]">{produto.marca}</span>}
                </div>
                <div className="flex flex-1 flex-col p-5">
                  <h3 className="text-2xl font-semibold text-[#294b41]">{produto.nome || "Produto sem nome"}</h3>
                  <p className="mt-2 line-clamp-2 min-h-12 text-sm leading-6 text-[#748079]">{produto.descricao || "Consulte a disponibilidade diretamente no mercadinho."}</p>
                  <div className="mt-auto flex items-end justify-between gap-4 border-t border-[#ebe5db] pt-5">
                    <div>{produto.validade && <span className="block text-xs text-[#89928d]">Validade: {formatDate(produto.validade)}</span>}{produto.codigoBarras && <span className="mt-1 block max-w-36 truncate text-xs text-[#89928d]">Cód. {produto.codigoBarras}</span>}</div>
                    <strong className="font-display text-2xl font-semibold text-[#286858]">{formatCurrency(produto.preco)}</strong>
                  </div>
                </div>
              </article>
            ))}
          </div>
        )}

        <section className="mt-10 rounded-3xl bg-[#f4d7c6] p-6 sm:flex sm:items-center sm:justify-between sm:gap-6 sm:p-8"><div><h2 className="text-2xl font-semibold text-[#244c41]">Trabalha no Mercadinho JMM?</h2><p className="mt-2 text-sm leading-6 text-[#756a64]">A administração de produtos e vendas fica em uma área reservada.</p></div><Link href="/acesso" className="button-primary mt-5 sm:mt-0">Acessar administração →</Link></section>
      </main>
    </div>
  );
}
