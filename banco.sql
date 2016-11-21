create database Carrinho_Compras;
use Carrinho_Compras;

create table if not exists Usuario (
	id int primary key not null auto_increment,
	login varchar(30),
	senha varchar(30),
	nome varchar(90),
    administrador boolean);

create table if not exists Carrinho (
	id int primary key not null auto_increment,
	data date);

create table if not exists Item (
	id int primary key not null auto_increment,
	descricao varchar(120),
	preco real);

create table if not exists Itens_Carrinho (
	Carrinho_id int not null,
	Item_id int not null,
	quantidade_item int,
	foreign key (Carrinho_id)
	references Carrinho(id),
	foreign key (Item_id) 
	references Item(id),
    unique (Carrinho_id, Item_id));

create table if not exists Carrinhos_Usuario (
	Usuario_id int not null,
	Carrinho_id int not null,
	foreign key (Usuario_id) 
	references Usuario(id),
	foreign key (Carrinho_id)
	references Carrinho(id),
    unique (Usuario_id, Carrinho_id));
