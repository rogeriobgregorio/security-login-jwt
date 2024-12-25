#!/bin/bash

# Parametros de espera para o host e porta
host="$1"
shift
port="$1"
shift

# Aguardar até que o host esteja disponível
until nc -z "$host" "$port"; do
  echo "Aguardando $host:$port..."
  sleep 1
done

# Continuar com o comando após a espera
exec "$@"
