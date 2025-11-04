# 📸 Como Adicionar Imagens Reais dos Produtos

## 📁 ONDE COLOCAR AS IMAGENS

```
src/main/resources/static/images/products/
```

## 📋 LISTA DE IMAGENS NECESSÁRIAS

### 🍔 Hamburgueres
- `big-mac.jpg` - Foto do Big Mac Classic
- `cheddar-bacon.jpg` - Foto do Cheddar Bacon Supreme

### 🍟 Acompanhamentos  
- `batata-frita.jpg` - Foto da Batata Frita
- `onion-rings.jpg` - Foto da Cebola Empanada

### ⚡ Energéticos
- `red-bull.jpg` - Foto do Red Bull
- `monster.jpg` - Foto do Monster Energy

### 🍺 Bebidas Alcoólicas
- `heineken.jpg` - Foto da Heineken
- `budweiser.jpg` - Foto da Budweiser
- `corona.jpg` - Foto da Corona

### 🥤 Refrigerantes
- `coca-cola.jpg` - Foto da Coca-Cola
- `guarana.jpg` - Foto do Guaraná
- `sprite.jpg` - Foto do Sprite
- `fanta.jpg` - Foto da Fanta

## 🎨 RECOMENDAÇÕES PARA AS FOTOS

### Tamanho Ideal
- **Largura:** 800px - 1200px
- **Altura:** 600px - 900px
- **Proporção:** 4:3 ou 16:9
- **Formato:** JPG, PNG ou WebP

### Qualidade
- ✅ Alta resolução para zoom
- ✅ Boa iluminação
- ✅ Fundo limpo ou neutro
- ✅ Produto centralizado
- ✅ Foto apetitosa e atraente

### Otimização
- Comprimir as imagens (use TinyPNG.com ou similar)
- Tamanho máximo: 200-500KB por imagem
- Qualidade: 80-90% é suficiente

## 🚀 COMO ADICIONAR

### Método 1: Copiar Direto
```bash
# Cole suas fotos na pasta:
cp /caminho/suas/fotos/*.jpg src/main/resources/static/images/products/
```

### Método 2: Via Interface
1. Navegue até a pasta do projeto
2. Vá em: `src/main/resources/static/images/products/`
3. Cole ou arraste suas imagens
4. Renomeie conforme a lista acima

### Método 3: Download (se tiver URLs)
```bash
cd src/main/resources/static/images/products/
wget https://exemplo.com/big-mac.jpg -O big-mac.jpg
wget https://exemplo.com/cheddar-bacon.jpg -O cheddar-bacon.jpg
# ... e assim por diante
```

## ✅ VERIFICAR SE FUNCIONOU

### 1. Reinicie a Aplicação
```bash
mvn spring-boot:run
```

### 2. Acesse
```
http://localhost:8080/orders/new
```

### 3. Verifique
- As imagens dos produtos devem aparecer
- Se não aparecer, verifique:
  - Nome do arquivo está correto?
  - Está na pasta certa?
  - A aplicação foi reiniciada?

## 🔍 TESTAR SE IMAGEM ESTÁ ACESSÍVEL

Abra no navegador:
```
http://localhost:8080/images/products/big-mac.jpg
http://localhost:8080/images/products/batata-frita.jpg
http://localhost:8080/images/products/coca-cola.jpg
```

Se abrir a imagem = ✅ Funcionando!

## 📝 NOMES DOS ARQUIVOS (EXATO)

```
big-mac.jpg
cheddar-bacon.jpg
batata-frita.jpg
onion-rings.jpg
red-bull.jpg
monster.jpg
heineken.jpg
budweiser.jpg
corona.jpg
coca-cola.jpg
guarana.jpg
sprite.jpg
fanta.jpg
```

**Importante:** Use exatamente estes nomes (minúsculas, com hífen)!

## 🎨 PLACEHOLDERS ATUAIS

Atualmente existem placeholders SVG coloridos em cada arquivo:
- 🍔 Emoji + nome do produto
- Fundo colorido
- Texto do nome

**Você pode substituir estes arquivos pelas fotos reais!**

## 🖼️ EXEMPLOS DE ONDE CONSEGUIR IMAGENS

### Opção 1: Tire Fotos dos Seus Produtos
- Use uma boa câmera/celular
- Boa iluminação
- Fundo branco ou neutro

### Opção 2: Banco de Imagens Gratuitos
- Unsplash.com (busque "burger", "fries", etc.)
- Pexels.com
- Pixabay.com

### Opção 3: Imagens de Marca
- Site oficial dos produtos (Red Bull, Coca-Cola, etc.)
- **Atenção:** Verifique direitos de uso!

### Opção 4: AI Generated
- DALL-E, Midjourney, Stable Diffusion
- Prompts: "professional food photography of a cheeseburger"

## 🔄 SUBSTITUIR PLACEHOLDER

### Passo a Passo:
```bash
# 1. Navegue até a pasta
cd src/main/resources/static/images/products/

# 2. Veja os arquivos atuais
ls -lh

# 3. Substitua a imagem (exemplo)
# Renomeie sua foto para o nome correto
mv /caminho/sua/foto.jpg big-mac.jpg

# 4. Confirme
ls -lh big-mac.jpg

# 5. Reinicie a aplicação
cd ../../../../..
mvn spring-boot:run
```

## 💡 DICA PRO

Crie um script para redimensionar todas as imagens automaticamente:

```bash
# Usando ImageMagick (se instalado)
for img in *.jpg; do
  convert "$img" -resize 1200x900 -quality 85 "$img"
done
```

## ✨ RESULTADO ESPERADO

Depois de adicionar as imagens reais:
- Cards de produtos com fotos lindas
- Modal com imagem grande do produto
- Interface profissional
- Clientes veem exatamente o que vão receber

## 🎯 PRÓXIMO PASSO

1. ✅ Estrutura já está pronta
2. ✅ Placeholders estão funcionando
3. 📸 Adicione suas fotos reais
4. 🚀 Reinicie a aplicação
5. 🎉 Pronto!

---

**As imagens serão carregadas automaticamente da pasta /images/products/!**

**Basta adicionar os arquivos e reiniciar! 🍔📸**

