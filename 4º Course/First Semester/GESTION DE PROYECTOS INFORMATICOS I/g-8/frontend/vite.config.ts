import { defineConfig } from "vite";
import { resolve } from "path";
import react from "@vitejs/plugin-react";
import { glob } from "glob";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    rollupOptions: {
      input: glob.sync(resolve(__dirname, "pages/**/*.html")),
      output: {
        entryFileNames: `static/[name].js`,
        chunkFileNames: `static/[name].js`,
        assetFileNames: `static/[name].[ext]`,
      },
    },
    outDir: "../dist",
    emptyOutDir: true,
  },
  resolve: {
    alias: {
      "@": resolve(__dirname, "./src"),
    },
  },
});
