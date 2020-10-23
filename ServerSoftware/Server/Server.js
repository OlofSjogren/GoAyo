const express = require('express')
const fs = require('fs')

const app = express();

app.use(express.json())
app.use('/users', require('./routes/users'))
app.use('/groups', require('./routes/groups'))
app.use('/debts', require('./routes/debts'))
app.use('/login', require('./routes/login'))
app.use('/payments', require('./routes/payments'))

app.get('/', (req, res) => {
    const database = JSON.parse(fs.readFileSync("database.json"))
    res.send(database);
})

app.post('/test', (req,res) => {
    console.log(req.body);
    res.send();
})

const port = process.env.PORT || 9000;
app.listen(port, () => console.log(`Listening on port ${port}....`))
