import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'
import fs from 'fs';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 80,
    https: {
      key: fs.readFileSync('key.pem'),
      cert: fs.readFileSync('cert.pem'),
    }
  }
});

