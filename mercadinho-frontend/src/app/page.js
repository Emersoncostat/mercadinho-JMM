"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import Link from "next/link";
import AppHeader from "@/components/AppHeader";

const PRODUCTS_URL = "http://localhost:8082/api/produtos";

const formatCurrency = (value) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(Number(value) || 0);

function ArrowIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M5 12h14M13 6l6 6-6 6" />
    </svg>
  );
}

function BoxIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-5 w-5" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
      <path d="m4 7 8-4 8 4-8 4-8-4Z" />
      <path d="M4 7v10l8 4 8-4V7M12 11v10" />
    </svg>
  );
}

function LockIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-5 w-5" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
      <rect x="5" y="10" width="14" height="11" rx="2" />
      <path d="M8 10V7a4 4 0 0 1 8 0v3" />
    </svg>
  );
}

function RefreshIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
      <path d="M20 6v5h-5M4 18v-5h5" />
      <path d="M6.1 9a7 7 0 0 1 11.6-2.6L20 11M4 13l2.3 4.6A7 7 0 0 0 17.9 15" />
    </svg>
  );
}

export default function Home() {
  const [produtos, setProdutos] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [apiDisponivel, setApiDisponivel] = useState(true);

  const carregarResumo = useCallback(async () => {
    try {
      setCarregando(true);
      const productsResponse = await fetch(PRODUCTS_URL);
      if (!productsResponse.ok) throw new Error("Falha ao carregar o resumo");

      const productsData = await productsResponse.json();
      setProdutos(Array.isArray(productsData) ? productsData : []);
      setApiDisponivel(true);
    } catch (error) {
      console.error("Erro ao carregar o resumo:", error);
      setApiDisponivel(false);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    // A carga inicial sincroniza a interface com a API do projeto.
    // eslint-disable-next-line react-hooks/set-state-in-effect
    carregarResumo();
  }, [carregarResumo]);

  const produtosRecentes = useMemo(() => [...produtos].reverse().slice(0, 3), [produtos]);
  const marcas = useMemo(() => new Set(produtos.map((produto) => produto.marca).filter(Boolean)).size, [produtos]);
  const menorPreco = useMemo(() => produtos.length ? Math.min(...produtos.map((produto) => Number(produto.preco) || 0)) : 0, [produtos]);

  return (
    <div className="page-shell">
      <AppHeader active="inicio" />

      <main className="content-width py-7 sm:py-10">
        <section className="surface-card rise-in grid overflow-hidden lg:grid-cols-[1.08fr_0.92fr]">
          <div className="relative px-6 py-10 sm:px-10 sm:py-14 lg:px-14 lg:py-16">
            <div aria-hidden="true" className="absolute left-0 top-0 h-2 w-full bg-[linear-gradient(90deg,#a8ccb8_0_44%,#f4d7c6_44%_72%,#f3e3a5_72%)]" />
            <p className="eyebrow">Seu mercadinho de confiança</p>
            <h1 className="mt-5 max-w-2xl text-4xl font-semibold leading-[1.08] tracking-[-0.045em] text-[#1e5043] sm:text-5xl lg:text-[3.55rem]">
              O que você precisa, <span className="text-[#d06f60]">pertinho de casa.</span>
            </h1>
            <p className="mt-6 max-w-xl text-base leading-7 text-[#62736b] sm:text-lg">
              Alimentos, bebidas, itens de higiene e aquela compra rápida que resolve o dia — com preço justo e atendimento de vizinhança.
            </p>
            <div className="mt-8 flex flex-wrap gap-3">
              <Link href="/produtos" className="button-primary">
                Ver produtos <ArrowIcon />
              </Link>
              <Link href="/acesso" className="button-secondary">
                Acesso da equipe
              </Link>
            </div>

            <div className="mt-9 flex flex-wrap items-center gap-x-5 gap-y-2 border-t border-[#e8e1d7] pt-5 text-sm text-[#718078]">
              <span className="inline-flex items-center gap-2"><i className="h-2 w-2 rounded-full bg-[#7eaf91]" /> Variedade para o dia a dia</span>
              <span className="inline-flex items-center gap-2"><i className="h-2 w-2 rounded-full bg-[#dea274]" /> Compra simples e rápida</span>
            </div>
          </div>

          <aside className="flex flex-col bg-[#e8f3ea] p-5 sm:p-7 lg:p-9" aria-label="Resumo do catálogo">
            <div className="flex items-center justify-between gap-4">
              <div>
                <p className="text-xs font-extrabold uppercase tracking-[0.16em] text-[#4d7568]">Na prateleira</p>
                <h2 className="mt-1 text-2xl font-semibold text-[#214f43]">Produtos recentes</h2>
              </div>
              <button type="button" onClick={carregarResumo} disabled={carregando} className="button-quiet" aria-label="Atualizar resumo">
                <RefreshIcon /> <span className="hidden sm:inline">Atualizar</span>
              </button>
            </div>

            <div className="mt-6 flex-1 space-y-3">
              {carregando ? (
                [1, 2, 3].map((item) => <div key={item} className="h-[72px] animate-pulse rounded-2xl bg-white/70" />)
              ) : produtosRecentes.length === 0 ? (
                <div className="grid min-h-52 place-items-center rounded-2xl border border-dashed border-[#abc4b6] bg-white/55 p-6 text-center">
                  <div>
                    <span className="mx-auto grid h-11 w-11 place-items-center rounded-xl bg-[#f3e3a5] text-[#315e51]"><BoxIcon /></span>
                    <p className="mt-4 font-bold text-[#315e51]">O catálogo ainda está vazio</p>
                    <p className="mt-1 text-sm leading-6 text-[#75827b]">Cadastre os primeiros produtos para vê-los aqui.</p>
                  </div>
                </div>
              ) : (
                produtosRecentes.map((produto, index) => (
                  <div key={produto.codigo ?? `${produto.nome}-${index}`} className="flex items-center gap-4 rounded-2xl border border-white/80 bg-white/85 p-3.5 shadow-[0_8px_22px_rgba(42,86,72,0.06)]">
                    <span className="grid h-11 w-11 shrink-0 place-items-center rounded-xl bg-[#f8ead0] font-display text-lg font-semibold text-[#936d44]">
                      {(produto.nome || "P").charAt(0).toUpperCase()}
                    </span>
                    <span className="min-w-0 flex-1">
                      <strong className="block truncate text-sm text-[#294a40]">{produto.nome || "Produto sem nome"}</strong>
                      <span className="mt-0.5 block text-xs text-[#839088]">Código #{produto.codigo ?? "—"}</span>
                    </span>
                    <strong className="text-sm text-[#286858]">{formatCurrency(produto.preco)}</strong>
                  </div>
                ))
              )}
            </div>

            <Link href="/produtos" className="mt-5 inline-flex items-center justify-between rounded-2xl bg-[#286858] px-4 py-3.5 text-sm font-bold text-white transition hover:bg-[#174b40]">
              Abrir catálogo completo <ArrowIcon />
            </Link>
          </aside>
        </section>

        <section className="mt-6 grid gap-4 sm:grid-cols-3" aria-label="Resumo do mercadinho">
          <div className="surface-card flex items-center gap-4 p-5">
            <span className="grid h-11 w-11 place-items-center rounded-2xl bg-[#dcefe3] text-[#286858]"><BoxIcon /></span>
            <div><strong className="font-display block text-2xl font-semibold text-[#244c41]">{carregando ? "—" : produtos.length}</strong><span className="text-sm text-[#748079]">produtos cadastrados</span></div>
          </div>
          <div className="surface-card flex items-center gap-4 p-5">
            <span className="grid h-11 w-11 place-items-center rounded-2xl bg-[#fae8dd] text-[#b56759]">M</span>
            <div><strong className="font-display block text-2xl font-semibold text-[#244c41]">{carregando ? "—" : marcas}</strong><span className="text-sm text-[#748079]">marcas diferentes</span></div>
          </div>
          <div className="surface-card flex items-center gap-4 p-5">
            <span className="grid h-11 w-11 place-items-center rounded-2xl bg-[#f7edc8] text-[#8b7133]">R$</span>
            <div><strong className="font-display block text-xl font-semibold text-[#244c41]">{carregando ? "—" : formatCurrency(menorPreco)}</strong><span className="text-sm text-[#748079]">menor preço</span></div>
          </div>
        </section>

        {!apiDisponivel && (
          <div className="status-error mt-4 flex flex-wrap items-center justify-between gap-3" role="status">
            <span>Não foi possível atualizar os dados. Confirme se o backend está rodando na porta 8082.</span>
            <button type="button" onClick={carregarResumo} className="font-bold underline underline-offset-4">Tentar novamente</button>
          </div>
        )}

        <section className="mt-10" aria-labelledby="quick-actions-title">
          <div className="flex flex-col gap-2 sm:flex-row sm:items-end sm:justify-between">
            <div>
              <p className="eyebrow">Acesso rápido</p>
              <h2 id="quick-actions-title" className="mt-2 text-3xl font-semibold tracking-[-0.035em] text-[#234b40]">O que você quer fazer?</h2>
            </div>
            <p className="text-sm text-[#748079]">Atalhos para as tarefas mais usadas</p>
          </div>

          <div className="mt-5 grid gap-5 md:grid-cols-2">
            <Link href="/produtos" className="group surface-card relative overflow-hidden p-6 transition hover:-translate-y-1 hover:shadow-[0_24px_52px_rgba(36,73,61,0.1)] sm:p-7">
              <span aria-hidden="true" className="absolute -right-12 -top-12 h-36 w-36 rounded-full bg-[#dcefe3]" />
              <div className="relative flex items-start justify-between gap-6">
                <span className="grid h-12 w-12 place-items-center rounded-2xl bg-[#dcefe3] text-[#286858]"><BoxIcon /></span>
                <span className="grid h-9 w-9 place-items-center rounded-full border border-[#d7d0c5] text-[#286858] transition group-hover:border-[#286858] group-hover:bg-[#286858] group-hover:text-white"><ArrowIcon /></span>
              </div>
              <div className="relative mt-8">
                <p className="text-xs font-extrabold uppercase tracking-[0.16em] text-[#628173]">Catálogo</p>
                <h3 className="mt-2 text-2xl font-semibold text-[#234b40]">Cadastrar e consultar produtos</h3>
                <p className="mt-2 max-w-md text-sm leading-6 text-[#718078]">Inclua novos itens, pesquise por nome ou código e organize os preços.</p>
              </div>
            </Link>

            <Link href="/acesso" className="group surface-card relative overflow-hidden p-6 transition hover:-translate-y-1 hover:shadow-[0_24px_52px_rgba(36,73,61,0.1)] sm:p-7">
              <span aria-hidden="true" className="absolute -bottom-14 -right-8 h-40 w-40 rounded-full bg-[#f4d7c6]" />
              <div className="relative flex items-start justify-between gap-6">
                <span className="grid h-12 w-12 place-items-center rounded-2xl bg-[#fae8dd] text-[#b76759]"><LockIcon /></span>
                <span className="grid h-9 w-9 place-items-center rounded-full border border-[#d7d0c5] text-[#286858] transition group-hover:border-[#286858] group-hover:bg-[#286858] group-hover:text-white"><ArrowIcon /></span>
              </div>
              <div className="relative mt-8">
                <p className="text-xs font-extrabold uppercase tracking-[0.16em] text-[#9a6c60]">Equipe</p>
                <h3 className="mt-2 text-2xl font-semibold text-[#234b40]">Acessar a administração</h3>
                <p className="mt-2 max-w-md text-sm leading-6 text-[#718078]">Área reservada para gerenciar produtos, vendas, estoque e demais rotinas.</p>
              </div>
            </Link>
          </div>
        </section>

        <footer className="mt-12 border-t border-[#ded7cc] py-6 text-center text-xs font-semibold text-[#7c8881]">
          Mercadinho JMM · Variedade e praticidade perto de você
        </footer>
      </main>
    </div>
  );
}
