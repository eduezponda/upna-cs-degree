import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { Toaster } from "./ui/toaster";

interface HeaderFooterContainerProps {
  children: React.ReactNode;
}

const OneColumnLayout: React.FC<HeaderFooterContainerProps> = ({
  children,
}) => {
  return (
    <div className="relative flex min-h-screen flex-col bg-background">
      <Header />
      <main className="flex flex-1 justify-center mt-8">
        <div className="w-4/5">{children}</div>
      </main>
      <Toaster />
      <Footer />
    </div>
  );
};

export default OneColumnLayout;
