"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import Link from "next/link";
import { apiFetch } from "@/lib/api";

const formatCurrency = (value) => new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(Number(value) || 0);

const modules = [
  { href: "/admin/clientes", label: "Clientes", description: "Contatos e dados cadastrais", tone: "bg-[#fae8dd] text-[#a55f53]", icon: "C" },
  { href: "/admin/fornecedores", label: "Fornecedores", description: "Parceiros e abastecimento", tone: "bg-[#f7edc8] text-[#80682e]", icon: "F" },
  { href: "/admin/funcionarios", label: "Equipe", description: "Funcionários e acessos", tone: "bg-[#dcefe3] text-[#286858]", icon: "E" },
  { href: "/admin/estoque", label: "Estoque", description: "Níveis e reposição", tone: "bg-[#dcefe3] text-[#286858]", icon: "Q" },
  { href: "/admin/lotes", label: "Lotes", description: "Entradas e validades", tone: "bg-[#f7edc8] text-[#80682e]", icon: "L" },
  { href: "/admin/caixas", label: "Caixas", description: "Aberturas e fechamentos", tone: "bg-[#fae8dd] text-[#a55f53]", icon: "$" },
  { href: "/admin/pagamentos", label: "Pagamentos", description: "Recebimentos e troco", tone: "bg-[#f7edc8] text-[#80682e]", icon: "Pg" },
  { href: "/admin/financeiro", label: "Financeiro", description: "Receitas e despesas", tone: "bg-[#dcefe3] text-[#286858]", icon: "R$" },
  { href: "/admin/mercado", label: "Mercado", description: "Contato e funcionamento", tone: "bg-[#fae8dd] text-[#a55f53]", icon: "M" },
];

export default function AdminDashboardPage() {
  const [dados, setDados] = useState({ produtos: [], vendas: [], clientes: [], estoques: [] });
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(false);

  const carregar = useCallback(async () => {
    try {
      setCarregando(true);
      const [produtos, vendas, clientes, estoques] = await Promise.all([
        apiFetch("/api/produtos"),
        apiFetch("/api/vendas"),
        apiFetch("/api/clientes"),
        apiFetch("/api/estoques"),
      ]);
      setDados({
        produtos: Array.isArray(produtos) ? produtos : [],
        vendas: Array.isArray(vendas) ? vendas : [],
        clientes: Array.isArray(clientes) ? clientes : [],
        estoques: Array.isArray(estoques) ? estoques : [],
      });
      setErro(false);
    } catch (error) {
      console.error("Erro ao carregar painel:", error);
      setErro(true);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect
    carregar();
  }, [carregar]);

  const resumo = useMemo(() => ({
    faturamento: dados.vendas.reduce((total, venda) => total + (Number(venda.valorTotal) || 0), 0),
    itens: dados.vendas.reduce((total, venda) => total + (Number(venda.quantidadeProdutos) || 0), 0),
    alertas: dados.estoques.filter((estoque) => Number(estoque.quantidadeDisponivel) <= Number(estoque.estoqueMinimo)).length,
  }), [dados]);

  const vendasRecentes = useMemo(() => [...dados.vendas].sort((a, b) => (Number(b.id) || 0) - (Number(a.id) || 0)).slice(0, 4), [dados.vendas]);

  return (
    <div className="rise-in">
      <div className="flex flex-col gap-4 lg:flex-row lg:items-end lg:justify-between">
        <div>
          <p className="eyebrow">Visão geral</p>
          <h1 className="mt-2 text-4xl font-semibold tracking-[-0.045em] text-[#214f43]">Bom trabalho hoje.</h1>
          <p className="mt-3 max-w-2xl text-base leading-7 text-[#6d7b74]">Acompanhe o movimento e acesse rapidamente as tarefas do mercadinho.</p>
        </div>
        <div className="flex flex-wrap gap-3"><button type="button" onClick={carregar} disabled={carregando} className="button-secondary">↻ Atualizar dados</button><Link href="/admin/vendas" className="button-primary">+ Nova venda</Link></div>
      </div>

      {erro && <div className="status-error mt-5">Não foi possível atualizar todos os indicadores. Verifique o backend e tente novamente.</div>}

      <section className="mt-6 grid gap-4 sm:grid-cols-2 xl:grid-cols-4" aria-label="Indicadores principais">
        {[
          ["Produtos", dados.produtos.length, "itens no catálogo", "bg-[#dcefe3] text-[#286858]", "P"],
          ["Vendas", dados.vendas.length, `${resumo.itens} itens vendidos`, "bg-[#fae8dd] text-[#a55f53]", "V"],
          ["Faturamento", formatCurrency(resumo.faturamento), "total registrado", "bg-[#f7edc8] text-[#80682e]", "R$"],
          ["Estoque baixo", resumo.alertas, "registros para revisar", "bg-[#f5ded8] text-[#a65349]", "!"],
        ].map(([title, value, caption, tone, icon]) => (
          <div key={title} className="surface-card flex items-center gap-4 p-5">
            <span className={`grid h-12 w-12 shrink-0 place-items-center rounded-2xl font-display font-semibold ${tone}`}>{icon}</span>
            <div className="min-w-0"><span className="block text-sm font-bold text-[#65756d]">{title}</span><strong className="font-display mt-1 block truncate text-2xl font-semibold text-[#244c41]">{carregando ? "—" : value}</strong><span className="text-xs text-[#89928d]">{caption}</span></div>
          </div>
        ))}
      </section>

      <div className="mt-6 grid gap-6 xl:grid-cols-[1.05fr_0.95fr]">
        <section className="surface-card overflow-hidden">
          <div className="flex items-center justify-between gap-4 border-b border-[#e2dbcf] px-6 py-5"><div><h2 className="text-2xl font-semibold text-[#244c41]">Movimento recente</h2><p className="mt-1 text-sm text-[#748079]">Últimas vendas registradas</p></div><Link href="/admin/vendas" className="button-quiet">Ver todas →</Link></div>
          {carregando ? (
            <div className="space-y-3 p-6">{[1, 2, 3].map((item) => <div key={item} className="h-14 animate-pulse rounded-2xl bg-[#f0eee8]" />)}</div>
          ) : vendasRecentes.length === 0 ? (
            <div className="p-10 text-center"><p className="font-display text-xl font-semibold text-[#345248]">O caixa ainda está quieto</p><p className="mt-2 text-sm text-[#7c8881]">As primeiras vendas aparecerão aqui.</p><Link href="/admin/vendas" className="button-primary mt-5">Registrar venda</Link></div>
          ) : (
            <div className="divide-y divide-[#ebe5db]">
              {vendasRecentes.map((venda) => <div key={venda.id} className="flex items-center gap-4 px-6 py-4"><span className="grid h-10 w-10 place-items-center rounded-xl bg-[#fae8dd] text-sm font-bold text-[#a55f53]">#{venda.id}</span><div className="min-w-0 flex-1"><strong className="block text-sm text-[#345248]">{venda.quantidadeProdutos || 0} itens</strong><span className="text-xs text-[#89928d]">{venda.dataVenda || "Data não informada"}</span></div><strong className="text-sm text-[#286858]">{formatCurrency(venda.valorTotal)}</strong></div>)}
            </div>
          )}
        </section>

        <section className="surface-card overflow-hidden">
          <div className="border-b border-[#e2dbcf] bg-[#e2f0e5] px-6 py-5"><h2 className="text-2xl font-semibold text-[#244c41]">Ações rápidas</h2><p className="mt-1 text-sm text-[#677970]">Comece pelas rotinas mais frequentes.</p></div>
          <div className="grid gap-3 p-5 sm:grid-cols-2">
            <Link href="/admin/produtos" className="rounded-2xl border border-[#e3ddd2] bg-white p-4 transition hover:-translate-y-0.5 hover:border-[#b9cfc2]"><span className="grid h-9 w-9 place-items-center rounded-xl bg-[#dcefe3] font-bold text-[#286858]">+</span><strong className="mt-4 block text-[#345248]">Adicionar produto</strong><span className="mt-1 block text-xs leading-5 text-[#7c8881]">Atualize o catálogo público</span></Link>
            <Link href="/admin/clientes" className="rounded-2xl border border-[#e3ddd2] bg-white p-4 transition hover:-translate-y-0.5 hover:border-[#edcfc2]"><span className="grid h-9 w-9 place-items-center rounded-xl bg-[#fae8dd] font-bold text-[#a55f53]">+</span><strong className="mt-4 block text-[#345248]">Cadastrar cliente</strong><span className="mt-1 block text-xs leading-5 text-[#7c8881]">Guarde os dados de contato</span></Link>
            <Link href="/admin/estoque" className="rounded-2xl border border-[#e3ddd2] bg-white p-4 transition hover:-translate-y-0.5 hover:border-[#b9cfc2]"><span className="grid h-9 w-9 place-items-center rounded-xl bg-[#dcefe3] font-bold text-[#286858]">Q</span><strong className="mt-4 block text-[#345248]">Conferir estoque</strong><span className="mt-1 block text-xs leading-5 text-[#7c8881]">Revise níveis mínimos</span></Link>
            <Link href="/admin/financeiro" className="rounded-2xl border border-[#e3ddd2] bg-white p-4 transition hover:-translate-y-0.5 hover:border-[#eadca9]"><span className="grid h-9 w-9 place-items-center rounded-xl bg-[#f7edc8] text-xs font-bold text-[#80682e]">R$</span><strong className="mt-4 block text-[#345248]">Lançar movimento</strong><span className="mt-1 block text-xs leading-5 text-[#7c8881]">Registre receitas e despesas</span></Link>
          </div>
        </section>
      </div>

      <section className="mt-8" aria-labelledby="areas-title">
        <div><p className="eyebrow">Todas as áreas</p><h2 id="areas-title" className="mt-2 text-3xl font-semibold text-[#244c41]">Administração do mercado</h2></div>
        <div className="mt-5 grid gap-3 sm:grid-cols-2 lg:grid-cols-3">
          {modules.map((module) => <Link key={module.href} href={module.href} className="surface-card group flex items-center gap-4 p-4 transition hover:-translate-y-0.5"><span className={`grid h-11 w-11 shrink-0 place-items-center rounded-2xl text-xs font-bold ${module.tone}`}>{module.icon}</span><span className="min-w-0 flex-1"><strong className="block text-sm text-[#345248]">{module.label}</strong><span className="mt-0.5 block truncate text-xs text-[#89928d]">{module.description}</span></span><span className="text-[#8aa095] transition group-hover:translate-x-1 group-hover:text-[#286858]">→</span></Link>)}
        </div>
      </section>
    </div>
  );
}
