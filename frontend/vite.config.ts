import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { apiUrl } from './src/utils/ExportRoute'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 80,
    host: true,
  },
  base: "/",
})
