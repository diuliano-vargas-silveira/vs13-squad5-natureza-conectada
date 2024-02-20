----------------------------------------
--------------- Task 01 ----------------
----------------------------------------


-- Criar DB

criar vemserdbc
use vemserdbc
db.createCollection("usuarios")

-- Inserções

db.usuarios.insertMany([
{
	"nome" : "Rafael Lazzari", 
	"email" : "rafael@dbc.com.br",
	"senha" : "123456",
	"tipo_usuario" : "CLIENTE",
	"ativo" : "Ativo.A",
	"cpf" : "33344455560"
},{ 
	"nome": "Maria Silva",
	"email": "maria@gmail.com",
	"senha": "senha123",
	"tipo_usuario": "CLIENTE",
	"ativo": "Ativo.A",
	"cpf": "12345678901"
},{
	"nome": "João Oliveira",
 	"email": "joao@hotmail.com",
    	"senha": "abc123",
    	"tipo_usuario": "ADMINISTRADOR",
    	"ativo": "Ativo.A",
    	"cpf": "98765432109"
},{
	"nome": "Ana Souza",
    	"email": "ana@yahoo.com",
    	"senha": "senha456",
    	"tipo_usuario": "CLIENTE",
    	"ativo": "Ativo.A",
    	"cpf": "45678901234"}])

-- Buscas

db.usuarios.find()

// Resposta

{
  _id: ObjectId('65d41f72d1c23af147126492'),
  nome: 'Rafael Lazzari',
  email: 'rafael@dbc.com.br',
  senha: '123456',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '33344455560'
}
{
  _id: ObjectId('65d419b1f5c9c64488f068a4'),
  nome: 'João Oliveira',
  email: 'joao@hotmail.com',
  senha: 'abc123',
  tipo_usuario: 'ADMINISTRADOR',
  ativo: 'Ativo.A',
  cpf: '98765432109'
}
{
  _id: ObjectId('65d419bbf5c9c64488f068a5'),
  nome: 'Ana Souza',
  email: 'ana@yahoo.com',
  senha: 'senha456',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '45678901234'
}
{
  _id: ObjectId('65d41a6af5c9c64488f068a6'),
  nome: 'Maria Silva',
  email: 'maria@gmail.com',
  senha: 'senha123',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '12345678901'
}
-------------------------------------------------------------------------
db.usuarios.find({"nome" : "Rafael Lazzari"}).pretty()

// Resposta

{
  _id: ObjectId('65d41f72d1c23af147126492'),
  nome: 'Rafael Lazzari',
  email: 'rafael@dbc.com.br',
  senha: '123456',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '33344455560'
}
-------------------------------------------------------------------------
db.usuarios.find({"cpf": /56.8/})

// Resposta

{
  _id: ObjectId('65d41f72d1c23af147126493'),
  nome: 'Maria Silva',
  email: 'maria@gmail.com',
  senha: 'senha123',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '12345678901'
}
{
  _id: ObjectId('65d41f72d1c23af147126495'),
  nome: 'Ana Souza',
  email: 'ana@yahoo.com',
  senha: 'senha456',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '45678901234'
}
-------------------------------------------------------------------------
db.usuarios.find({"nome": /ar/})

// Resposta

{
  _id: ObjectId('65d41f72d1c23af147126492'),
  nome: 'Rafael Lazzari',
  email: 'rafael@dbc.com.br',
  senha: '123456',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '33344455560'
}
{
  _id: ObjectId('65d41f72d1c23af147126493'),
  nome: 'Maria Silva',
  email: 'maria@gmail.com',
  senha: 'senha123',
  tipo_usuario: 'CLIENTE',
  ativo: 'Ativo.A',
  cpf: '12345678901'
}
-------------------------------------------------------------------------
db.usuarios.updateOne({ email: 'rafael@dbc.com.br' }, { $set: { nome: 'Rafael Prudente' }})

// Resposta

{
  acknowledged: true,
  insertedId: null,
  matchedCount: 1,
  modifiedCount: 1,
  upsertedCount: 0
}