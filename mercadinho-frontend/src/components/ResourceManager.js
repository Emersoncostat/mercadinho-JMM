"use client";

import { useCallback, useEffect, useMemo, useState } from "react";
import { apiFetch } from "@/lib/api";
import { resourceConfigs } from "@/lib/resources";

const accentStyles = {
  mint: { header: "bg-[#e2f0e5]", icon: "bg-white text-[#286858]", badge: "bg-[#dcefe3] text-[#286858]" },
  peach: { header: "bg-[#fae8dd]", icon: "bg-white text-[#b56759]", badge: "bg-[#fae8dd] text-[#a45f53]" },
  butter: { header: "bg-[#f7edc8]", icon: "bg-white text-[#8b7133]", badge: "bg-[#f7edc8] text-[#80682e]" },
};

const formatCurrency = (value) =>
  new Intl.NumberFormat("pt-BR", { style: "currency", currency: "BRL" }).format(Number(value) || 0);

const formatDate = (value, withTime = false) => {
  if (!value) return "—";
  const normalized = String(value).length === 10 ? `${value}T12:00:00` : value;
  const date = new Date(normalized);
  if (Number.isNaN(date.getTime())) return value;
  return new Intl.DateTimeFormat("pt-BR", withTime ? { dateStyle: "short", timeStyle: "short" } : { dateStyle: "short" }).format(date);
};

function SearchIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round"><circle cx="11" cy="11" r="7" /><path d="m20 20-4-4" /></svg>;
}

function RefreshIcon() {
  return <svg aria-hidden="true" viewBox="0 0 24 24" className="h-4 w-4" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M20 6v5h-5M4 18v-5h5" /><path d="M6.1 9a7 7 0 0 1 11.6-2.6L20 11M4 13l2.3 4.6A7 7 0 0 0 17.9 15" /></svg>;
}

export default function ResourceManager({ resourceKey }) {
  const config = resourceConfigs[resourceKey];
  const emptyForm = useMemo(() => Object.fromEntries(config.fields.map((field) => [field.name, ""])), [config.fields]);
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [busca, setBusca] = useState("");
  const [carregando, setCarregando] = useState(true);
  const [salvando, setSalvando] = useState(false);
  const [editandoId, setEditandoId] = useState(null);
  const [confirmandoId, setConfirmandoId] = useState(null);
  const [mensagem, setMensagem] = useState(null);
  const accent = accentStyles[config.accent] || accentStyles.mint;

  const carregar = useCallback(async () => {
    try {
      setCarregando(true);
      const data = await apiFetch(config.endpoint);
      setItems(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error(`Erro ao carregar ${config.title}:`, error);
      setMensagem({ tipo: "erro", texto: `Não foi possível carregar ${config.title.toLowerCase()}.` });
    } finally {
      setCarregando(false);
    }
  }, [config.endpoint, config.title]);

  useEffect(() => {
    // eslint-disable-next-line react-hooks/set-state-in-effect
    carregar();
  }, [carregar]);

  const filtrados = useMemo(() => {
    const termo = busca.trim().toLocaleLowerCase("pt-BR");
    if (!termo) return items;
    return items.filter((item) => JSON.stringify(item).toLocaleLowerCase("pt-BR").includes(termo));
  }, [busca, items]);

  const atualizarCampo = (name, value) => setForm((current) => ({ ...current, [name]: value }));

  const limparFormulario = () => {
    setForm(emptyForm);
    setEditandoId(null);
    setMensagem(null);
  };

  const enviar = async (event) => {
    event.preventDefault();
    setMensagem(null);

    const payload = Object.fromEntries(config.fields.map((field) => {
      const value = form[field.name];
      if (field.numeric) return [field.name, value === "" ? null : Number(value)];
      return [field.name, value || null];
    }));

    try {
      setSalvando(true);
      const editing = editandoId !== null;
      await apiFetch(editing ? `${config.endpoint}/${editandoId}` : config.endpoint, {
        method: editing ? "PUT" : "POST",
        body: JSON.stringify(payload),
      });
      setMensagem({ tipo: "sucesso", texto: editing ? "Alterações salvas com sucesso." : `${config.singular.charAt(0).toUpperCase()}${config.singular.slice(1)} cadastrado com sucesso.` });
      setForm(emptyForm);
      setEditandoId(null);
      await carregar();
    } catch (error) {
      console.error(`Erro ao salvar ${config.singular}:`, error);
      setMensagem({ tipo: "erro", texto: `Não foi possível salvar o ${config.singular}. Confira os dados e tente novamente.` });
    } finally {
      setSalvando(false);
    }
  };

  const editar = (item) => {
    const values = Object.fromEntries(config.fields.map((field) => [field.name, item[field.name] ?? ""]));
    setForm(values);
    setEditandoId(item[config.idKey]);
    setMensagem(null);
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  const excluir = async (item) => {
    const id = item[config.idKey];
    try {
      await apiFetch(`${config.endpoint}/${id}`, { method: "DELETE" });
      setConfirmandoId(null);
      setMensagem({ tipo: "sucesso", texto: "Registro excluído com sucesso." });
      await carregar();
    } catch (error) {
      console.error(`Erro ao excluir ${config.singular}:`, error);
      setMensagem({ tipo: "erro", texto: "O registro não pôde ser excluído. Ele pode estar sendo utilizado em outra área." });
    }
  };

  const formatCell = (column, value) => {
    if (column.format === "currency") return formatCurrency(value);
    if (column.format === "date") return formatDate(value);
    if (column.format === "dateTime") return formatDate(value, true);
    if (column.format === "code") return <span className={`inline-flex rounded-lg px-2.5 py-1.5 text-xs font-extrabold ${accent.badge}`}>#{value ?? "—"}</span>;
    if (column.format === "status") return <span className={`inline-flex rounded-full px-2.5 py-1 text-xs font-bold ${accent.badge}`}>{value || "—"}</span>;
    return value || "—";
  };

  return (
    <div className="rise-in">
      <div className="flex flex-col gap-4 xl:flex-row xl:items-end xl:justify-between">
        <div>
          <p className="eyebrow">{config.eyebrow}</p>
          <h1 className="mt-2 text-4xl font-semibold tracking-[-0.045em] text-[#214f43]">{config.title}</h1>
          <p className="mt-3 max-w-2xl text-base leading-7 text-[#6d7b74]">{config.description}</p>
        </div>
        <div className="surface-card flex min-w-44 items-center gap-4 p-4">
          <span className={`grid h-11 w-11 place-items-center rounded-2xl font-display text-lg font-semibold ${accent.badge}`}>{items.length}</span>
          <div><strong className="block text-sm text-[#345248]">Registros</strong><span className="text-xs text-[#7c8881]">disponíveis agora</span></div>
        </div>
      </div>

      <div className="mt-6 grid items-start gap-6 xl:grid-cols-[0.78fr_1.22fr]">
        <section className="surface-card overflow-hidden">
          <div className={`border-b border-[#dfd8cc] px-6 py-6 ${accent.header}`}>
            <span className={`grid h-10 w-10 place-items-center rounded-xl text-xl shadow-[0_7px_18px_rgba(40,104,88,0.07)] ${accent.icon}`}>{editandoId !== null ? "✎" : "+"}</span>
            <h2 className="mt-5 text-2xl font-semibold text-[#244c41]">{editandoId !== null ? `Editar ${config.singular}` : `Novo ${config.singular}`}</h2>
            <p className="mt-1 text-sm leading-6 text-[#677970]">Preencha os campos abaixo para salvar o registro.</p>
          </div>

          <form onSubmit={enviar} className="grid gap-5 p-6 sm:grid-cols-2">
            {config.fields.map((field) => (
              <label key={field.name} className={field.wide ? "sm:col-span-2" : ""}>
                <span className="mb-2 block text-sm font-bold text-[#37564c]">{field.label}</span>
                {field.type === "select" ? (
                  <select value={form[field.name]} onChange={(event) => atualizarCampo(field.name, event.target.value)} className="field" required={field.required}>
                    <option value="">Selecione</option>
                    {field.options.map((option) => <option key={option} value={option}>{option}</option>)}
                  </select>
                ) : (
                  <input type={field.type || "text"} value={form[field.name]} onChange={(event) => atualizarCampo(field.name, event.target.value)} placeholder={field.placeholder} min={field.min} step={field.step} className="field" required={field.required} autoComplete={field.type === "password" ? "new-password" : "off"} />
                )}
              </label>
            ))}

            <div className="grid grid-cols-2 gap-3 sm:col-span-2">
              <button type="button" onClick={limparFormulario} className="button-secondary">{editandoId !== null ? "Cancelar" : "Limpar"}</button>
              <button type="submit" disabled={salvando} className="button-primary">{salvando ? "Salvando..." : editandoId !== null ? "Salvar alterações" : "Cadastrar"}</button>
            </div>

            {mensagem && <p className={`${mensagem.tipo === "sucesso" ? "status-success" : "status-error"} sm:col-span-2`} role="status">{mensagem.texto}</p>}
          </form>
        </section>

        <section className="surface-card overflow-hidden">
          <div className="border-b border-[#e2dbcf] p-5 sm:p-6">
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
              <div><h2 className="text-2xl font-semibold text-[#244c41]">Registros cadastrados</h2><p className="mt-1 text-sm text-[#748079]">{filtrados.length} de {items.length} registros exibidos</p></div>
              <button type="button" onClick={carregar} disabled={carregando} className="button-secondary self-start sm:self-auto"><RefreshIcon /> Atualizar</button>
            </div>
            <label className="relative mt-5 block">
              <span className="sr-only">Buscar registros</span>
              <span className="pointer-events-none absolute inset-y-0 left-0 grid w-11 place-items-center text-[#708078]"><SearchIcon /></span>
              <input type="search" value={busca} onChange={(event) => setBusca(event.target.value)} placeholder="Buscar em todos os campos..." className="field field-search" />
            </label>
          </div>

          {carregando ? (
            <div className="space-y-3 p-6">{[1, 2, 3].map((item) => <div key={item} className="h-16 animate-pulse rounded-2xl bg-[#f0eee8]" />)}</div>
          ) : items.length === 0 ? (
            <div className="grid min-h-72 place-items-center px-6 py-12 text-center"><div><span className={`mx-auto grid h-14 w-14 place-items-center rounded-2xl text-xl ${accent.badge}`}>+</span><h3 className="mt-4 text-xl font-semibold text-[#294b41]">Nenhum registro ainda</h3><p className="mx-auto mt-2 max-w-xs text-sm leading-6 text-[#748079]">Use o formulário para adicionar o primeiro.</p></div></div>
          ) : filtrados.length === 0 ? (
            <div className="grid min-h-60 place-items-center px-6 py-10 text-center"><div><span className={`mx-auto grid h-14 w-14 place-items-center rounded-2xl ${accent.badge}`}><SearchIcon /></span><h3 className="mt-4 text-xl font-semibold text-[#294b41]">Nada encontrado</h3><button type="button" onClick={() => setBusca("")} className="button-secondary mt-4">Limpar busca</button></div></div>
          ) : (
            <div className="overflow-x-auto">
              <table className="w-full min-w-[720px] border-collapse text-left">
                <thead><tr className="bg-[#fbf8f1] text-xs font-extrabold uppercase tracking-[0.08em] text-[#78847e]">{config.columns.map((column) => <th key={column.key} className="px-4 py-3.5 first:pl-6">{column.label}</th>)}<th className="px-4 py-3.5 pr-6 text-right">Ações</th></tr></thead>
                <tbody className="divide-y divide-[#ebe5db]">
                  {filtrados.map((item) => {
                    const id = item[config.idKey];
                    return (
                      <tr key={id} className="transition hover:bg-[#f4f8f2]">
                        {config.columns.map((column) => <td key={column.key} className="max-w-60 truncate px-4 py-4 first:pl-6 text-sm font-semibold text-[#4f625a]">{formatCell(column, item[column.key])}</td>)}
                        <td className="px-4 py-4 pr-6 text-right">
                          {confirmandoId === id ? (
                            <span className="inline-flex items-center gap-2"><button type="button" onClick={() => setConfirmandoId(null)} className="button-quiet">Cancelar</button><button type="button" onClick={() => excluir(item)} className="rounded-xl bg-[#b85d52] px-3 py-2 text-xs font-bold text-white">Confirmar</button></span>
                          ) : (
                            <span className="inline-flex items-center gap-1">{config.canEdit && <button type="button" onClick={() => editar(item)} className="button-quiet">Editar</button>}<button type="button" onClick={() => setConfirmandoId(id)} className="button-quiet text-[#ad5a50] hover:bg-[#fff0eb]">Excluir</button></span>
                          )}
                        </td>
                      </tr>
                    );
                  })}
                </tbody>
              </table>
            </div>
          )}
        </section>
      </div>
    </div>
  );
}
