import Link from "next/link";

export default function Home() {
  return (
      <main className="min-h-screen p-8 bg-gray-50">
        <div className="max-w-4xl mx-auto">
          <h1 className="text-3xl font-bold text-gray-800 mb-2">
            Mercadinho-JMM
          </h1>
          <p className="text-gray-600 mb-8">
            Seja bem-vindo! Escolha uma das opções abaixo para gerenciar o sistema.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <Link
                href="/produtos"
                className="p-6 bg-white rounded-lg shadow hover:shadow-md transition border border-gray-200"
            >
              <h2 className="text-xl font-semibold text-blue-600 mb-2">
                Gerenciar Produtos &rarr;
              </h2>
              <p className="text-sm text-gray-500">
                Visualize, adicione ou edite os produtos do estoque.
              </p>
            </Link>

            <Link
                href="/vendas"
                className="p-6 bg-white rounded-lg shadow hover:shadow-md transition border border-gray-200"
            >
              <h2 className="text-xl font-semibold text-green-600 mb-2">
                Caixa / Vendas &rarr;
              </h2>
              <p className="text-sm text-gray-500">
                Registre novas vendas e consulte o histórico.
              </p>
            </Link>
          </div>
        </div>
      </main>
  );
}