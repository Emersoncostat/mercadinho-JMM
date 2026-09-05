"use client";

import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import AppHeader from "@/components/AppHeader";
import { apiFetch, TOKEN_KEY, USER_KEY } from "@/lib/api";

function UserIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-6 w-6" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round"><circle cx="12" cy="8" r="4" /><path d="M4.5 21a7.5 7.5 0 0 1 15 0" /></svg>;
}

function LockIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-6 w-6" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round"><rect x="5" y="10" width="14" height="11" rx="2" /><path d="M8 10V7a4 4 0 0 1 8 0v3" /></svg>;
}

export default function AcessoPage() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [entrando, setEntrando] = useState(false);
  const [erro, setErro] = useState("");

  const entrar = async (event) => {
    event.preventDefault();
    setErro("");
    try {
      setEntrando(true);
      const data = await apiFetch("/api/auth/login", {
        method: "POST",
        body: JSON.stringify({ email: email.trim(), senha }),
      });
      localStorage.setItem(TOKEN_KEY, data.token);
      localStorage.setItem(USER_KEY, JSON.stringify(data));
      router.push("/admin");
    } catch (error) {
      console.error("Erro de acesso:", error);
      setErro(error.message || "E-mail ou senha inválidos.");
    } finally {
      setEntrando(false);
    }
  };

  return (
    <div className="page-shell">
      <AppHeader active="acesso" />
      <main className="content-width py-8 sm:py-12">
        <div className="mx-auto max-w-5xl">
          <div className="text-center">
            <p className="eyebrow">Escolha como continuar</p>
            <h1 className="mt-3 text-4xl font-semibold tracking-[-0.045em] text-[#214f43] sm:text-5xl">Clientes e equipe têm espaços diferentes.</h1>
            <p className="mx-auto mt-4 max-w-2xl text-base leading-7 text-[#6d7b74]">O catálogo fica aberto para consulta. Cadastros, preços e vendas são reservados à administração.</p>
          </div>

          <div className="mt-8 grid gap-6 lg:grid-cols-2">
            <section className="surface-card relative overflow-hidden p-6 sm:p-8">
              <span aria-hidden="true" className="absolute -right-14 -top-14 h-40 w-40 rounded-full bg-[#dcefe3]" />
              <div className="relative">
                <span className="grid h-12 w-12 place-items-center rounded-2xl bg-[#dcefe3] text-[#286858]"><UserIcon /></span>
                <p className="mt-7 text-xs font-extrabold uppercase tracking-[0.16em] text-[#638075]">Para clientes</p>
                <h2 className="mt-2 text-3xl font-semibold text-[#244c41]">Consultar produtos</h2>
                <p className="mt-3 max-w-md leading-7 text-[#6d7b74]">Veja os itens disponíveis, confira marcas, preços e informações do catálogo sem precisar entrar.</p>
                <Link href="/produtos" className="button-primary mt-8">Abrir catálogo <span>→</span></Link>
              </div>
            </section>

            <section className="surface-card overflow-hidden">
              <div className="border-b border-[#e2dbcf] bg-[#fae8dd] px-6 py-6 sm:px-8">
                <span className="grid h-12 w-12 place-items-center rounded-2xl bg-white text-[#a66054]"><LockIcon /></span>
                <p className="mt-6 text-xs font-extrabold uppercase tracking-[0.16em] text-[#996d63]">Para administradores</p>
                <h2 className="mt-2 text-3xl font-semibold text-[#244c41]">Entrar na administração</h2>
                <p className="mt-2 text-sm leading-6 text-[#756d68]">Use o e-mail e a senha cadastrados para um administrador ou gerente.</p>
              </div>
              <form onSubmit={entrar} className="space-y-5 p-6 sm:p-8">
                <label className="block"><span className="mb-2 block text-sm font-bold text-[#37564c]">E-mail</span><input type="email" value={email} onChange={(event) => setEmail(event.target.value)} className="field" placeholder="admin@mercadinho.com" autoComplete="username" required /></label>
                <label className="block"><span className="mb-2 block text-sm font-bold text-[#37564c]">Senha</span><input type="password" value={senha} onChange={(event) => setSenha(event.target.value)} className="field" placeholder="Digite sua senha" autoComplete="current-password" required /></label>
                {erro && <p className="status-error" role="alert">{erro}</p>}
                <button type="submit" disabled={entrando} className="button-primary w-full">{entrando ? "Entrando..." : "Entrar na área administrativa"}</button>
              </form>
            </section>
          </div>
        </div>
      </main>
    </div>
  );
}
