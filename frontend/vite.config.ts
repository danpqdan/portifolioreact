import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 80,
    https: true,
    cors: {
      origin: 'http://54.232.19.163:8080',
      methods: ['GET', 'POST', 'PUT', 'DELETE'],
      credentials: true,
    },
  }
})
