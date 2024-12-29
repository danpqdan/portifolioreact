import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 80,
    https: false,
    cors: {
      origin: 'http://18.230.255.100:8080',
      methods: ['GET', 'POST', 'PUT', 'DELETE'],
      credentials: true,
    },
  }
})
