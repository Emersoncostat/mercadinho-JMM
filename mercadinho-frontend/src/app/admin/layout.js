import AdminShell from "@/components/AdminShell";

export const metadata = {
  title: "Administração",
};

export default function AdminLayout({ children }) {
  return <AdminShell>{children}</AdminShell>;
}
