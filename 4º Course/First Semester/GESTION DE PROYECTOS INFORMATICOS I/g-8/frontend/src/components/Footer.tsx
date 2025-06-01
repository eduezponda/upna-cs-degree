import React from "react";
import { Shell } from "lucide-react";

const Footer: React.FC = () => {
  return (
    <footer className="py-6 md:px-8 md:py-0">
      <div className="container flex flex-col items-center justify-between gap-4 md:h-24 md:flex-row">
        <div className="flex flex-row align-middle space-x-1 mx-8">
          <p className="text-sm text-muted-foreground">
            Página diseñada y construida por <strong>Mazapan Software</strong>
          </p>
          <Shell className="opacity-60" />
        </div>
      </div>
    </footer>
  );
};

export default Footer;
