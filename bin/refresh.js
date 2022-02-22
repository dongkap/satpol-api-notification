#!/usr/bin/env node

async function refresh() {

  await fetch('https://satpol-api-notification.herokuapp.com/')

}

refresh();