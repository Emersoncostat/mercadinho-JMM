import Link from "next/link";

const navItems = [
  { href: "/", label: "Início", key: "inicio" },
  { href: "/produtos", label: "Produtos", key: "produtos" },
  { href: "/acesso", label: "Acesso", key: "acesso" },
];

function BasketIcon() {
  return (
    <svg aria-hidden="true" viewBox="0 0 24 24" className="h-5 w-5" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round">
      <path d="M4 9h16l-1.2 9.2a2 2 0 0 1-2 1.8H7.2a2 2 0 0 1-2-1.8L4 9Z" />
      <path d="m8 9 4-6 4 6M9 13v3M15 13v3" />
    </svg>
  );
}

export default function AppHeader({ active = "inicio" }) {
  return (
    <header className="sticky top-0 z-40 border-b border-[#e4ded3] bg-[#fffdf8]/95 backdrop-blur-md">
      <div className="content-width flex min-h-[74px] items-center justify-between gap-3 py-3">
        <Link href="/" className="flex min-w-0 items-center gap-3" aria-label="Página inicial do Mercadinho JMM">
          <span className="grid h-11 w-11 shrink-0 place-items-center rounded-2xl bg-[#dcefe3] text-[#245f50] shadow-[0_8px_18px_rgba(36,95,80,0.12)]">
            <BasketIcon />
          </span>
          <span className="min-w-0">
            <span className="font-display block truncate text-lg font-semibold leading-tight tracking-[-0.025em] text-[#1f4f43] sm:text-xl">
              Mercadinho JMM
            </span>
            <span className="hidden text-xs font-semibold text-[#7a867f] sm:block">Tudo para a sua rotina</span>
          </span>
        </Link>

        <nav aria-label="Navegação principal" className="flex items-center gap-1 rounded-2xl border border-[#e2dbcf] bg-white p-1 shadow-[0_7px_20px_rgba(36,73,61,0.06)]">
          {navItems.map((item) => {
            const isActive = item.key === active;

            return (
              <Link
                key={item.key}
                href={item.href}
                aria-current={isActive ? "page" : undefined}
                className={`rounded-xl px-2.5 py-2 text-xs font-bold transition sm:px-4 sm:text-sm ${
                  isActive
                    ? "bg-[#286858] text-white shadow-[0_5px_12px_rgba(40,104,88,0.18)]"
                    : "text-[#65736c] hover:bg-[#edf7ef] hover:text-[#1f5d50]"
                }`}
              >
                {item.label}
              </Link>
            );
          })}
        </nav>
      </div>
    </header>
  );
}
