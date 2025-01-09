/** @type {import('tailwindcss').Config} */
export default {
  purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: 'class', // or 'media' or 'class'
  content: [],
  theme: {
    extend: {
      fontFamily: {
        "lato" : ["Lato", "sans-serif"]
      }
    },
  },
  plugins: [],
}

