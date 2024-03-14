import { Container, InputAdornment, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import React from "react";
import { Account } from "./types/Account";
import { EditAccountDialog } from "./EditAccountDialog";
import NewAccountDialog from "./NewAccountDialog";
import { ResponseGetAccount } from "./types/Response";

export default function PasswordVault() {
  const [accounts, setAccounts] = React.useState<Account[]>([]);
  const [searchResult, setSearchResult] = React.useState<Account[]>([]);

  React.useEffect(() => {
    const getAccounts = async() => {
      const response = await fetch("http://localhost:8080/api/account/find/accounts", {
        method: 'GET',
        mode: 'cors',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
        credentials: 'include'
      });

      const responseBody: ResponseGetAccount = await response.json();
      setAccounts(responseBody.response);
      setSearchResult(responseBody.response);
    }
    getAccounts();
  }, [])

  const handleSearch = (searchQuery: string) => {
    if (!searchQuery.length) {
      setSearchResult(accounts);
    } else {
      setSearchResult([...accounts].filter((account) => {
        return account.account_name.toLowerCase().includes(searchQuery.toLowerCase()) || 
              account.account_username.toLowerCase().includes(searchQuery.toLowerCase())
      }));
    }
  }

  return (
    <TableContainer component={Container} sx={{padding: "20px"}}>
        <Stack spacing={2}>
          <Typography variant="h4">Password Vaults</Typography>
          <Stack direction="row" spacing={2}>
              <TextField
                id="filled-search"
                label="Search field"
                type="search"
                variant="outlined"
                onChange={(e) => handleSearch(e.target.value)}
                InputProps={{
                  startAdornment: (
                    <InputAdornment position="start">
                      <SearchIcon />
                    </InputAdornment>
                  ),
                }}
              />
            <NewAccountDialog accounts={accounts} setAccounts={setAccounts}/>
          </Stack>
        </Stack>
      <Table aria-label="password vault table">
        <TableHead>
          <TableRow>
            <TableCell><h3>ACCOUNT NAME</h3></TableCell>
            <TableCell><h3>USERNAME</h3></TableCell>
            <TableCell><h3>PASSWORD</h3></TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {searchResult.map((row) => (
            <EditAccountDialog key={row.id} props={{
              id: row.id, 
              accountname: row.account_name, 
              username: row.account_username, 
              password: row.account_password}}
            />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}