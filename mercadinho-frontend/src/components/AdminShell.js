"use client";

import { useEffect, useState } from "react";
import Link from "next/link";
import { usePathname, useRouter } from "next/navigation";
import { apiFetch, TOKEN_KEY, USER_KEY } from "@/lib/api";

const groups = [
  {
    label: "Principal",
    items: [
      { href: "/admin", label: "Visão geral", icon: "⌂" },
      { href: "/admin/produtos", label: "Produtos", icon: "P" },
      { href: "/admin/vendas", label: "Vendas", icon: "V" },
    ],
  },
  {
    label: "Cadastros",
    items: [
      { href: "/admin/clientes", label: "Clientes", icon: "C" },
      { href: "/admin/fornecedores", label: "Fornecedores", icon: "F" },
      { href: "/admin/funcionarios", label: "Equipe", icon: "E" },
    ],
  },
  {
    label: "Operação",
    items: [
      { href: "/admin/estoque", label: "Estoque", icon: "Q" },
      { href: "/admin/lotes", label: "Lotes", icon: "L" },
      { href: "/admin/caixas", label: "Caixas", icon: "$" },
      { href: "/admin/pagamentos", label: "Pagamentos", icon: "Pg" },
      { href: "/admin/financeiro", label: "Financeiro", icon: "R$" },
      { href: "/admin/mercado", label: "Mercado", icon: "M" },
    ],
  },
];

function BasketIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-5 w-5" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
      <path d="M4 9h16l-1.2 9.2a2 2 0 0 1-2 1.8H7.2a2 2 0 0 1-2-1.8L4 9Z" />
      <path d="m8 9 4-6 4 6M9 13v3M15 13v3" />
    </svg>
  );
}

export default function AdminShell({ children }) {
  const pathname = usePathname();
  const router = useRouter();
  const [user, setUser] = useState(null);
  const [checking, setChecking] = useState(true);
  const [menuOpen, setMenuOpen] = useState(false);

  useEffect(() => {
    let ativo = true;

    const validarSessao = async () => {
      const token = localStorage.getItem(TOKEN_KEY);
      if (!token) {
        router.replace("/acesso");
        return;
      }

      try {
        const data = await apiFetch("/api/auth/me");
        if (!ativo) return;
        setUser(data);
        localStorage.setItem(USER_KEY, JSON.stringify(data));
      } catch (error) {
        console.error("Sessão inválida:", error);
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(USER_KEY);
        router.replace("/acesso");
      } finally {
        if (ativo) setChecking(false);
      }
    };

    const expirar = () => router.replace("/acesso");
    window.addEventListener("mercadinho-auth-expired", expirar);
    validarSessao();

    return () => {
      ativo = false;
      window.removeEventListener("mercadinho-auth-expired", expirar);
    };
  }, [router]);

  const sair = async () => {
    try {
      await apiFetch("/api/auth/logout", { method: "POST" });
    } catch (error) {
      console.error("Não foi possível encerrar a sessão no servidor:", error);
    } finally {
      localStorage.removeItem(TOKEN_KEY);
      localStorage.removeItem(USER_KEY);
      router.replace("/");
    }
  };

  if (checking || !user) {
    return (
      <div className="page-shell grid min-h-screen place-items-center p-6">
        <div className="surface-card flex items-center gap-4 px-6 py-5 text-[#537067]">
          <span className="h-4 w-4 animate-spin rounded-full border-2 border-[#b8cec1] border-t-[#286858]" />
          Validando acesso administrativo...
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#f7f3eb] text-[#213d35]">
      <header className="sticky top-0 z-50 border-b border-[#e2dbcf] bg-[#fffdf8]/95 backdrop-blur-md">
        <div className="flex min-h-[72px] items-center justify-between gap-3 px-4 sm:px-6">
          <div className="flex items-center gap-3">
            <button type="button" onClick={() => setMenuOpen((value) => !value)} className="grid h-10 w-10 place-items-center rounded-xl border border-[#ded7cc] bg-white text-xl text-[#286858] md:hidden" aria-expanded={menuOpen} aria-label="Abrir menu administrativo">☰</button>
            <Link href="/admin" className="flex items-center gap-3">
              <span className="grid h-10 w-10 place-items-center rounded-2xl bg-[#dcefe3] text-[#245f50]"><BasketIcon /></span>
              <span><strong className="font-display block text-lg leading-tight text-[#1f4f43]">Mercadinho JMM</strong><span className="hidden text-xs font-semibold text-[#7a867f] sm:block">Área administrativa</span></span>
            </Link>
          </div>

          <div className="flex items-center gap-2 sm:gap-4">
            <div className="hidden text-right sm:block"><strong className="block text-sm text-[#345248]">{user.nome}</strong><span className="text-xs text-[#7a867f]">{user.cargo}</span></div>
            <span className="grid h-10 w-10 place-items-center rounded-full bg-[#fae8dd] font-display font-semibold text-[#a66054]">{(user.nome || "A").charAt(0)}</span>
            <button type="button" onClick={sair} className="button-secondary px-3 py-2">Sair</button>
          </div>
        </div>
      </header>

      <div className="flex min-h-[calc(100vh-73px)]">
        <aside className={`${menuOpen ? "block" : "hidden"} fixed inset-x-0 bottom-0 top-[73px] z-40 overflow-y-auto border-r border-[#e2dbcf] bg-[#fffdf8] p-4 md:sticky md:top-[73px] md:block md:h-[calc(100vh-73px)] md:w-64 md:shrink-0`}>
          <nav aria-label="Administração" className="space-y-6">
            {groups.map((group) => (
              <div key={group.label}>
                <p className="px-3 text-xs font-extrabold uppercase tracking-[0.14em] text-[#98a199]">{group.label}</p>
                <div className="mt-2 space-y-1">
                  {group.items.map((item) => {
                    const active = item.href === "/admin" ? pathname === item.href : pathname.startsWith(item.href);
                    return (
                      <Link key={item.href} href={item.href} onClick={() => setMenuOpen(false)} className={`flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-bold transition ${active ? "bg-[#dcefe3] text-[#1f5d50]" : "text-[#66756d] hover:bg-[#f3f0e9] hover:text-[#1f5d50]"}`}>
                        <span className={`grid h-8 w-8 place-items-center rounded-lg text-xs ${active ? "bg-white text-[#286858]" : "bg-[#f3f0e9] text-[#7a867f]"}`}>{item.icon}</span>
                        {item.label}
                      </Link>
                    );
                  })}
                </div>
              </div>
            ))}
          </nav>
          <Link href="/produtos" className="mt-6 flex items-center justify-between rounded-2xl border border-[#ded7cc] bg-[#fbf8f1] p-4 text-sm font-bold text-[#286858]">Ver catálogo público <span>→</span></Link>
        </aside>

        <div className="min-w-0 flex-1 bg-[radial-gradient(circle_at_100%_0,rgba(244,215,198,0.5),transparent_24rem),#f7f3eb]">
          <main className="mx-auto w-full max-w-[1500px] px-4 py-7 sm:px-6 sm:py-9 lg:px-8">{children}</main>
        </div>
      </div>
    </div>
  );
}
